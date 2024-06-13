package com.softwaretesting.testing.customerManagement.service;

import com.softwaretesting.testing.model.Customer;
import com.softwaretesting.testing.exception.BadRequestException;
import com.softwaretesting.testing.exception.CustomerNotFoundException;
import com.softwaretesting.testing.dao.CustomerRepository;
import com.softwaretesting.testing.validator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerManagementServiceImp implements CustomerManagementService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerValidator customerValidator;


    @Transactional(readOnly = true)
    @Override
    public Collection<Customer> list() {
        return StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Customer findByUserName(String userName) {
        Optional<Customer> customer = customerRepository.findByUserName(userName);

        customerValidator.validate404(customer, "User-Name", userName);

        return customer.get();
    }

    @Transactional(readOnly = true)
    @Override
    public Customer findById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);

        customerValidator.validate404(customer, "id", String.valueOf(id));

        return customer.get();
    }

    @Transactional(readOnly = true)
    @Override
    public Customer selectCustomerByPhoneNumber(String phoneNumber) {
        Optional<Customer> customer = customerRepository.selectCustomerByPhoneNumber(phoneNumber);

        customerValidator.validate404(customer, "phone number", phoneNumber);

        return customer.get();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long customerId) {


        if(!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(
                    "Customer with id " + customerId + " does not exists");
        }

        customerRepository.deleteById(customerId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Customer addCustomer(Customer customer) {
        Optional<Customer> existsPhoneNumber = customerRepository.selectCustomerByPhoneNumber(customer.getPhoneNumber());

        if (existsPhoneNumber.isPresent()) {
            throw new BadRequestException(
                    "Phone Number " + customer.getPhoneNumber() + " taken");
        }
        return customerRepository.save(customer);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Collection<Customer> saveAll(List<Customer> customers) {
        return StreamSupport.stream(customerRepository.saveAll(customers).spliterator(), false)
                .collect(Collectors.toSet());
    }
}
