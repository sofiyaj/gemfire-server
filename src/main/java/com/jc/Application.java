package com.jc;

import org.apache.geode.cache.GemFireCache;
import org.gj.demo.domain.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.config.annotation.CacheServerApplication;

@SpringBootApplication
@CacheServerApplication(name = "GemFireContinuousQueryServer")
public class Application {

    @Bean(name = "Customers")
    PartitionedRegionFactoryBean<Long, Customer> customersRegion(GemFireCache gemfireCache) {
        PartitionedRegionFactoryBean<Long, Customer> customers = new PartitionedRegionFactoryBean<>();
        customers.setCache(gemfireCache);
        customers.setClose(false);
        customers.setPersistent(false);
        return customers;
    }
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
