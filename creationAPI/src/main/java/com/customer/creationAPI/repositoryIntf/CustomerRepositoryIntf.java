package com.customer.creationAPI.repositoryIntf;

import java.util.Date;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.customer.creationAPI.model.Customer;
import com.customer.creationAPI.model.CustomerGroup;
import com.customer.creationAPI.model.CustomerOccupation;

@Repository
public interface CustomerRepositoryIntf extends MongoRepository<Customer,String>{

	@Query(value="{'email' :?0}", exists=true)
	boolean existsByEmail(String email);

	@Query(value="{'occupation':?0 , 'dob':?1 ,'customer_group':?2}", exists=true)
	boolean existsByOccupationDobGroup(CustomerOccupation occupation, Date dob, CustomerGroup customer_group);

}
