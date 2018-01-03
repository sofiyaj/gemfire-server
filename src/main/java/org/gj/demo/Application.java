package org.gj.demo;

import static org.springframework.data.gemfire.util.ArrayUtils.asArray;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.ReplicatedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import org.apache.geode.cache.CacheListener;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.server.CacheServer;
import org.apache.geode.cache.util.CacheListenerAdapter;
import org.gj.demo.domain.Customer;

/**
 * The {@link Application} class is a Spring Boot application with an embedded GemFire peer cache
 * running a GemFire {@link CacheServer} for cache clients simulating placed {@link Order orders}
 * from {@link Customer customers} at a scheduled, fixed interval.
 *
 * This Spring Boot, GemFire (Cache) Server application enables GemFire cache clients to connect, register CQs
 * and receive CQ events (notifications) of when new {@link Order orders} are placed.
 *
 * @author John Blum
 * @see org.apache.geode.cache.CacheListener
 * @see org.apache.geode.cache.GemFireCache
 * @see org.apache.geode.cache.Region
 * @see org.apache.geode.cache.server.CacheServer
 * @see org.springframework.boot.SpringApplication
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see org.springframework.data.gemfire.config.annotation.ClientCacheApplication
 * @see org.springframework.data.gemfire.PartitionedRegionFactoryBean
 * @see org.springframework.data.gemfire.RegionLookupFactoryBean
 * @see org.springframework.data.gemfire.examples.domain.Address
 * @see org.springframework.data.gemfire.examples.domain.Customer
 * @see org.springframework.data.gemfire.examples.domain.EmailAddress
 * @see org.springframework.data.gemfire.examples.domain.LineItem
 * @see org.springframework.data.gemfire.examples.domain.Order
 * @see org.springframework.data.gemfire.examples.domain.Product
 * @see org.springframework.scheduling.annotation.EnableScheduling
 * @see org.springframework.scheduling.annotation.Scheduled
 * @since 2.0.0
 */
@SpringBootApplication
@EnableScheduling
@CacheServerApplication(name = "GemFireContinuousQueryServer")
@SuppressWarnings("unused")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean(name = "Customers")
	PartitionedRegionFactoryBean<Long, Customer> customersRegion(GemFireCache gemfireCache) {

		PartitionedRegionFactoryBean<Long, Customer> customers = new PartitionedRegionFactoryBean<>();

		customers.setCache(gemfireCache);
		customers.setClose(false);
		customers.setPersistent(false);

		return customers;
	}


	private static final AtomicLong id = new AtomicLong(0L);

	private static final List<Customer> customerList = new ArrayList<>();

	private static final Customer jonDoe = newCustomer(1,"Jon", "Doe");
	private static final Customer janeDoe = newCustomer(2,"Jane", "Doe");
	private static final Customer cookieDoe = newCustomer(3,"Cookie", "Doe");
	private static final Customer froDoe = newCustomer(4,"Fro", "Doe");
	private static final Customer hoDoe = newCustomer(5,"Ho", "Doe");
	private static final Customer lanDoe = newCustomer(7,"Lan", "Doe");
	private static final Customer pieDoe = newCustomer(11,"Pie", "Doe");
	private static final Customer sourDoe = newCustomer(23,"Sour", "Doe");
	private static final Customer jackHandy = newCustomer(232,"Jack", "Handy");
	private static Customer newCustomer(long id,String firstName, String lastName) {

		Customer customer = new Customer(id, firstName, lastName);


		customerList.add(customer);

		return customer;
	}
	private static Customer findCustomer(int index) {
		return customerList.get(index);
	}

	private static Customer findCustomerById(Long id) {
		return customerList.stream().filter(customer -> customer.getId().equals(id)).findAny().orElse(null);
	}


	@Resource(name = "Customers")
	private Region<Long, Customer> customers;

	private Random random = new Random(System.currentTimeMillis());

	@Scheduled(initialDelay = 2000L, fixedRate = 5000L)
	public void fulfillOrder() {

		int customerIndex = random.nextInt(customerList.size());

		Customer customer = save(findCustomer(customerIndex));

		
	}

	private Customer save(Customer customer) {
		this.customers.put(customer.getId(), customer);
		return customer;
	}

}
