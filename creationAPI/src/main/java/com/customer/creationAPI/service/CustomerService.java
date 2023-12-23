package com.customer.creationAPI.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.creationAPI.model.Customer;
import com.customer.creationAPI.model.CustomerGroup;
import com.customer.creationAPI.model.CustomerOccupation;
import com.customer.creationAPI.repositoryIntf.CustomerRepositoryIntf;
import com.customer.creationAPI.serviceIntf.CustomerServiceIntf;


@Service
public class CustomerService implements CustomerServiceIntf {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	CustomerRepositoryIntf customerRepository;
	
	@Override
	public Customer save(Customer customer) throws IllegalArgumentException, Exception{
		
			if(customer.getEmail().endsWith("@hikeon.tech")) {
				customer.setCustomer_group(CustomerGroup.hikeon);
			}
			else {
				if(customer.getOccupation()==CustomerOccupation.developer 
						|| customer.getOccupation()==CustomerOccupation.chef) {
					customer.setCustomer_group((customer.getOccupation()==CustomerOccupation.developer)
							?CustomerGroup.developer:CustomerGroup.chef);
				}
				else {
					customer.setCustomer_group(CustomerGroup.NA);
				}
			}
			
			if(isBelow18(customer.getDob())) {
				throw new IllegalArgumentException("Customer must be 18 years or older.");
			}
			
			if(customerRepository.existsByEmail(customer.getEmail())) {
				throw new IllegalArgumentException("Email is already registerd.");
			}
			
			if(customerRepository.existsByOccupationDobGroup(
					customer.getOccupation(),customer.getDob(),customer.getCustomer_group())) {
				throw new IllegalArgumentException("Combination of occupation, dob and customer group must be unique.");
			}
			try {
				
			return customerRepository.save(customer);
			}
		
			catch(Exception e) {
				LOGGER.error("Error during data save", e);
				throw new Exception("Something went wrong during data save.");
			}
	}

	private boolean isBelow18(Date dob) {
		LocalDate birthDate=dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate currentDate=LocalDate.now();
		return birthDate.plusYears(18).isAfter(currentDate);
	}

}
