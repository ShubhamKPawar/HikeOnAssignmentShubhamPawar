package com.customer.creationAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.creationAPI.model.Customer;
import com.customer.creationAPI.serviceIntf.CustomerServiceIntf;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	@Autowired
	CustomerServiceIntf customerService;

	@PostMapping("/add")
	public ResponseEntity<?> createCustomer(@RequestBody Customer customer){
		try {
			Customer addedCustomer=customerService.save(customer);
			return new ResponseEntity<Customer>(addedCustomer, HttpStatus.CREATED);
		}
		catch(IllegalArgumentException e) {
			return new ResponseEntity<String>("Bad Request:"+e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			return new ResponseEntity<String>("Internal server error:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
