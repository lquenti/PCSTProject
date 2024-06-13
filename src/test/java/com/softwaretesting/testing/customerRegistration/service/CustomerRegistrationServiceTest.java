package com.softwaretesting.testing.customerRegistration.service;

import com.softwaretesting.testing.dao.CustomerRepository;
import com.softwaretesting.testing.exception.BadRequestException;
import com.softwaretesting.testing.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import org.mockito.ArgumentMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRegistrationServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerRegistrationService service;

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

    @Test
    void registerNewCustomerWorks() {
        /* To understand this test, we have to look at the registerNewCustomer.
         *
         * It internally checks first whether the customer is already registered by
         * querying it via the selectCustomerByPhoneNumber.
         *
         * Thus, in order to fake that we have a new customer, the function has to
         * return Optional.empty()
         */
        when(repository.selectCustomerByPhoneNumber(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

        /* save worked mock */
        when(repository.save(customer)).thenReturn(customer);

        /* the actual test */
        assertEquals(service.registerNewCustomer(customer), customer);
    }

    @Test
    void registerNewCustomerWithKnownCustomerFails() {
        /* See comment above, now the internal lookup works */
        when(repository.selectCustomerByPhoneNumber(PHONENUMBER)).thenReturn(Optional.of(customer));
        var thrown = assertThrows(IllegalStateException.class, () -> service.registerNewCustomer(customer));
        assertEquals("You are already registered", thrown.getMessage());
    }

    @Test
    void registerNewCustomerWithUsedPhoneNumberFails() {
        var anotherCustomerUsingTheSamePhoneNumber = new Customer();
        anotherCustomerUsingTheSamePhoneNumber.setName("Maxi Musterdude");
        anotherCustomerUsingTheSamePhoneNumber.setUserName("mmuster1");
        anotherCustomerUsingTheSamePhoneNumber.setPhoneNumber("+49 123123123123");
        when(repository.selectCustomerByPhoneNumber(PHONENUMBER))
                .thenReturn(Optional.of(anotherCustomerUsingTheSamePhoneNumber));

        var thrown = assertThrows(BadRequestException.class, () -> service.registerNewCustomer(customer));

        assertEquals(thrown.getMessage(), "Phone Number " + PHONENUMBER + " taken");
    }


}