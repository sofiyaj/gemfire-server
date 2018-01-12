package com.jc.service;

import org.gj.demo.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.dao.DataImport;

/**
 * @Author: wangjie
 * @Description:
 * @Date: Created in 20:11 2018/1/8
 */
@Service
public class AppService {
	@Autowired
	DataImport dataImport;
    public Customer findCustomer(int index) {
        return findCustomer(index);
    }


    public Customer findCustomerById(Long id) {
        return findCustomerById(id);
    }

    public Customer save(Customer customer) {
        return save(customer);

    }


    public Customer addCustomer(long id, String firstName, String lastName) {
        Customer customer = new Customer(id, firstName, lastName);
        return customer;
    }
}
