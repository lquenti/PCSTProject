package com.softwaretesting.testing.validator;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static com.softwaretesting.testing.validator.PhoneNumberValidator.validate;

public class CustomerValidatorTest {
    /* fails with empty object */
    @Test
    public void emptyFails() {
        var customerValidator = new CustomerValidator();
        var emptyopt = Optional.empty();
        var exception = assertThrows(ResponseStatusException.class,
                () -> customerValidator.validate404(emptyopt, "label", "value"));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());

    }
    /* works with valid object of complex type */
    @Test
    public void complexTestWorks() {
        var customerValidator = new CustomerValidator();
        assertDoesNotThrow(() -> customerValidator.validate404(Optional.of(new Object()), "label", "value"));
    }

    /* works with valid object of primitive type */
    @Test
    public void primitiveTestWorks() {
        var customerValidator = new CustomerValidator();
        assertDoesNotThrow(() -> customerValidator.validate404(Optional.of(4), "label", "value"));
    }
}
