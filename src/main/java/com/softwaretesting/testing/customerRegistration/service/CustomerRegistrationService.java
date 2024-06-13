package com.softwaretesting.testing.customerRegistration.service;

import com.softwaretesting.testing.exception.BadRequestException;
import com.softwaretesting.testing.dao.CustomerRepository;
import com.softwaretesting.testing.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerRegistrationService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerRegistrationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public Customer registerNewCustomer(Customer customer) {
        Optional<Customer> existsPhoneNumber = customerRepository.selectCustomerByPhoneNumber(customer.getPhoneNumber());

        //TODO: Validate customer phone number

        if (existsPhoneNumber.isPresent()) {
            Customer existingCustomer = existsPhoneNumber.get();
            if (existingCustomer.getName().equals(customer.getName())){
                throw new IllegalStateException("You are already registered");
            }
            throw new BadRequestException(
                    "Phone Number " + customer.getPhoneNumber() + " taken");
        }

        return customerRepository.save(customer);
    }
}






