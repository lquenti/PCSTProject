package com.softwaretesting.testing.customerManagement.service;

import com.softwaretesting.testing.model.Customer;

import java.util.Collection;
import java.util.List;

public interface CustomerManagementService {

    Collection<Customer> list();
    Customer findByUserName(String userName);
    Customer findById(Long id);
    Customer selectCustomerByPhoneNumber(String phoneNumber);
    void delete(Long customerId);
    Customer addCustomer(Customer customer);
    Collection<Customer> saveAll(List<Customer> customers);
}
