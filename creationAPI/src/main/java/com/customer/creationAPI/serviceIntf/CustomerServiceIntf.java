package com.customer.creationAPI.serviceIntf;

import org.springframework.stereotype.Service;

import com.customer.creationAPI.model.Customer;

@Service
public interface CustomerServiceIntf {

	Customer save(Customer customer) throws IllegalArgumentException,Exception;

}
