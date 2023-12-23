package com.customer.creationAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CreationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreationApiApplication.class, args);
		System.out.println("Server started successfully...");
		System.out.println("Server Port: 8081");
		System.out.println("Please open postman and call api on http://localhost:8081/api/customer/add with POST method and json file containing cutomer details.");
	}

}
