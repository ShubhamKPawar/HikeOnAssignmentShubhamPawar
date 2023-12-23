package com.customer.creationAPI.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection="customerDetails")
public class Customer {
	
	public Customer() {
		
	}
	
	@Id
	private String id;
	private String name;
	@Indexed(unique=true)
	private String email;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dob;
	private CustomerOccupation occupation;
	private CustomerGroup customer_group;
	
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}
	public void setDob(String dob) {
        this.dob = parseDateString(dob);
    }

    private Date parseDateString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateString);
        }
    }

	public CustomerOccupation getOccupation() {
		return occupation;
	}

	public void setOccupation(CustomerOccupation occupation) {
		this.occupation = occupation;
	}

	public CustomerGroup getCustomer_group() {
		return customer_group;
	}

	public void setCustomer_group(CustomerGroup customer_group) {
		this.customer_group = customer_group;
	}

	public Customer(String name, String email, Date dob, CustomerOccupation occupation,
			CustomerGroup customer_group) {
		super();
		this.name = name;
		this.email = email;
		this.dob = dob;
		this.occupation = occupation;
		this.customer_group = customer_group;
	}
	
	public Customer(String name, String email, String dob, CustomerOccupation occupation,
			CustomerGroup customer_group) {
		super();
		this.name = name;
		this.email = email;
		this.dob = parseDateString(dob);
		this.occupation = occupation;
		this.customer_group = customer_group;
	}

	public Customer(String id, String name, String email, Date dob, CustomerOccupation occupation,
			CustomerGroup customer_group) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.dob = dob;
		this.occupation = occupation;
		this.customer_group = customer_group;
	}
	
	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", dob=" + dob + ", occupation="
				+ occupation + ", customer_group=" + customer_group + "]";
	}
	
}
