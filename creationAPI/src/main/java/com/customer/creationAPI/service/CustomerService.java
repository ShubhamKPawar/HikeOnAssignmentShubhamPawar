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
		
			//check if customer email domain is hikeon.tech
			if(customer.getEmail().endsWith("@hikeon.tech")) {
				//if yes set customer grp to hikeon
				customer.setCustomer_group(CustomerGroup.hikeon);
			}
			else {
				//check occupation developer or chef
				if(customer.getOccupation()==CustomerOccupation.developer 
						|| customer.getOccupation()==CustomerOccupation.chef) {
					//set customer grp based on occupation
					customer.setCustomer_group((customer.getOccupation()==CustomerOccupation.developer)
							?CustomerGroup.developer:CustomerGroup.chef);
				}
				else {
					//if customer grp is not hikeon,developer or chef then set to NA
					customer.setCustomer_group(CustomerGroup.NA);
				}
			}
			
			//check if customer is below 18 or not
			if(isBelow18(customer.getDob())) {
				throw new IllegalArgumentException("Customer must be 18 years or older.");
			}
			
			//check if email id already register
			if(customerRepository.existsByEmail(customer.getEmail())) {
				throw new IllegalArgumentException("Email is already registerd.");
			}
			
			//check if occupation,dob,customer group is already register
			if(customerRepository.existsByOccupationDobGroup(
					customer.getOccupation(),customer.getDob(),customer.getCustomer_group())) {
				throw new IllegalArgumentException("Combination of occupation, dob and customer group must be unique.");
			}
			try {
				//save data
				return customerRepository.save(customer);
			}
		
			catch(Exception e) {
				LOGGER.error("Error during data save", e);
				throw new Exception("Something went wrong during data save.");
			}
	}

	//function to check age is below 18 or not
	private boolean isBelow18(Date dob) {
		LocalDate birthDate=dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate currentDate=LocalDate.now();
		return birthDate.plusYears(18).isAfter(currentDate);
	}

}
