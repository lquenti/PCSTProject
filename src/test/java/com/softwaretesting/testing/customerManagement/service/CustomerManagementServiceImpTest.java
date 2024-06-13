package com.softwaretesting.testing.customerManagement.service;

import com.softwaretesting.testing.dao.CustomerRepository;
import com.softwaretesting.testing.exception.BadRequestException;
import com.softwaretesting.testing.exception.CustomerNotFoundException;
import com.softwaretesting.testing.model.Customer;
import com.softwaretesting.testing.validator.CustomerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerManagementServiceImpTest {
    @Mock
    private CustomerRepository repository;
    @Mock
    private CustomerValidator validator;

    @InjectMocks
    private CustomerManagementServiceImp service;

    private Customer customer;

    private static final String NAME = "Max Mustermann";
    private static final String USERNAME = "mmuster";
    private static final String PHONENUMBER = "+49 12345678910";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        customer = new Customer();
        customer.setName(NAME);
        customer.setUserName(USERNAME);
        customer.setPhoneNumber(PHONENUMBER);
    }

    /* list() tests */

    @Test
    void emptyListWorks() {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(service.list().isEmpty());
    }

    @Test
    void nonEmptyListWorks() {
        var expected = Set.of(customer);
        when(repository.findAll()).thenReturn(List.of(customer));
        assertEquals(service.list(), expected);
    }

    /* findByXXXX() tests */

    @Test
    void findByUserNameWorks() {
        when(repository.findByUserName(customer.getUserName())).thenReturn(Optional.of(customer));
        /* we do not have to mock that it does not throw */
        assertEquals(customer, service.findByUserName(USERNAME));
        verify(validator, times(1)).validate404(Optional.of(customer), "User-Name", USERNAME);
    }

    @Test
    void findByIdWorks() {
        when(repository.findById(customer.getId())).thenReturn(Optional.of(customer));
        assertEquals(customer, service.findById(customer.getId()));
        verify(validator, times(1)).validate404(Optional.of(customer), "id", String.valueOf(customer.getId()));
    }

    @Test
    void selectCustomerByPhoneNumberWorks() {
        when(repository.selectCustomerByPhoneNumber(customer.getPhoneNumber())).thenReturn(Optional.of(customer));
        assertEquals(service.selectCustomerByPhoneNumber(customer.getPhoneNumber()), customer);
        verify(validator, times(1)).validate404(Optional.of(customer), "phone number", customer.getPhoneNumber());
    }

    /* delete*/

    @Test
    void deleteNotExistFails() {
        when(repository.existsById(customer.getId())).thenReturn(false);
        var customerid = customer.getId();
        var thrown = assertThrows(CustomerNotFoundException.class, () -> service.delete(customerid));
        assertEquals(thrown.getMessage(), "Customer with id " + customer.getId() + " does not exists");
    }

    @Test
    void deleteWorks() {
        when(repository.existsById(customer.getId())).thenReturn(true);
        assertDoesNotThrow(() -> service.delete(customer.getId()));
        verify(repository, times(1)).deleteById(customer.getId());
    }

    /* add */

    @Test
    void addCustomerExistingFails() {
        when(repository.selectCustomerByPhoneNumber(customer.getPhoneNumber())).thenReturn(Optional.of(customer));
        var thrown = assertThrows(BadRequestException.class, () -> service.addCustomer(customer));
        assertEquals(thrown.getMessage(), "Phone Number " + customer.getPhoneNumber() + " taken");
    }

    @Test
    void addCustomerNewWorks() {
        when(repository.selectCustomerByPhoneNumber(customer.getPhoneNumber())).thenReturn(Optional.empty());
        when(repository.save(customer)).thenReturn(customer);

        assertEquals(customer, service.addCustomer(customer));
    }

    /* saveAll */

    @Test
    void saveAllEmptyWorks() {
        var input = new ArrayList<Customer>();
        when(repository.saveAll(input)).thenReturn(input);
        assertTrue(service.saveAll(input).isEmpty());
    }

    @Test
    void saveAllWorks() {
        var input = new ArrayList<Customer>();
        input.add(customer);
        when(repository.saveAll(input)).thenReturn(input);
        var output = service.saveAll(input);
        assertEquals(1, output.size());
        assertTrue(output.contains(customer));
    }

}