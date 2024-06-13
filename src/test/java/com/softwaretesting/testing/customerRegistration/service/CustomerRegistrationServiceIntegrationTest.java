package com.softwaretesting.testing.customerRegistration.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerRegistrationServiceIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    void registerNewCustomer() throws Exception {
        mockMvc.perform(
                        post("/api/v1/customer-registration/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\": 1, \"userName\": \"f1\", \"name\": \"l1\", \"phoneNumber\": \"+490001123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("f1"))
                .andExpect(jsonPath("$.name").value("l1"))
                .andExpect(jsonPath("$.phoneNumber").value("+490001123456"));
    }
    
    @Test
    void registerTakenPhoneNumber() {
        assertThrows(NestedServletException.class, () ->
                mockMvc.perform(
                        post("/api/v1/customer-registration/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"id\": 1, \"userName\": \"f1\", \"name\": \"l1\", \"phoneNumber\": \"+490001\"}")));
    }

}