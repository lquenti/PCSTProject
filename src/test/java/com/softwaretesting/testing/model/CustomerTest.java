package com.softwaretesting.testing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    /* We are not supposed to test it,
     * but this is needed for full
     * Mutation Test Strength...
     */
    @Test
    void testHashCode() {
        Customer c1 = new Customer(1L, "a", "a", "a");
        Customer c2 = new Customer(2L, "b", "b", "b");
        assertNotEquals(c1.hashCode(), c2.hashCode());
    }
}