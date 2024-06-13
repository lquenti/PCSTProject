package com.softwaretesting.testing.customerManagement.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerManagementServiceImpIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void getExistingCustomer() throws Exception {
        mvc.perform(get("/api/v1/customers/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("f1"))
                .andExpect(jsonPath("$.name").value("l1"))
                .andExpect(jsonPath("$.phoneNumber").value("+490001"));

    }

    @Test
    void getNonExistingCustomer() throws Exception {
        mvc.perform(get("/api/v1/customers/10000000").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    @Transactional
    void postPreviouslyDeletedCustomer() throws Exception {
        mvc.perform(delete("/api/v1/customers/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 2, \"userName\": \"f2\", \"name\": \"l2\", \"phoneNumber\": \"+490002\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("f2"))
                .andExpect(jsonPath("$.name").value("l2"))
                .andExpect(jsonPath("$.phoneNumber").value("+490002"));
    }

    @Test
    void postKnownCustomer() throws Exception {
        mvc.perform(post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"userName\": \"f1\", \"name\": \"l1\", \"phoneNumber\": \"+490001\"}"))
                .andExpect(status().is(400));
    }

    @Test
    @Transactional
    void deleteCustomer() throws Exception {
        mvc.perform(delete("/api/v1/customers/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void deleteCustomerTwoTimes() throws Exception {
        mvc.perform(delete("/api/v1/customers/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(delete("/api/v1/customers/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

}