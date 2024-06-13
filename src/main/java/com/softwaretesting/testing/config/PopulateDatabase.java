/**
 * The Spring Boot app triggers the run method on successful start up and 
 * the code basically initializes the in-memory h2 databsae with 20K records. 
 * 
 *
 */

package com.softwaretesting.testing.config;

import com.softwaretesting.testing.model.Customer;
import com.softwaretesting.testing.customerManagement.service.CustomerManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class PopulateDatabase implements ApplicationRunner {
	Logger logger = LoggerFactory.getLogger(PopulateDatabase.class);

	private CustomerManagementService customerManagementService;

	@Autowired
	public PopulateDatabase(CustomerManagementService customerManagementService) {
		this.customerManagementService = customerManagementService;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.debug("Populating the database with 20K Dummy Records ... In Progress");
		List<Customer> customers = new ArrayList<>();
		
		IntStream.range(1,20000).forEach(id -> {
			Customer customer = new Customer();
			customer.setUserName("f" + id);
			customer.setName("l" + id);
			customer.setPhoneNumber("+49000" + id);
			
			customers.add(customer);
		});

		customerManagementService.saveAll(customers);
		
		logger.debug("Database filled up!");
	}
}
