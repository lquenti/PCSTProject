package com.softwaretesting.testing.customerRegistration.controller;

import com.softwaretesting.testing.dto.inbound.CustomerInDTO;
import com.softwaretesting.testing.dto.outbound.CustomerOutDTO;
import com.softwaretesting.testing.model.Customer;
import com.softwaretesting.testing.customerRegistration.service.CustomerRegistrationService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/customer-registration")
public class CustomerRegistrationController {

    private final CustomerRegistrationService customerRegistrationService;

    public CustomerRegistrationController(CustomerRegistrationService customerRegistrationService) {
        this.customerRegistrationService = customerRegistrationService;
    }


    @PostMapping
    public CustomerOutDTO registerNewCustomer(@RequestBody CustomerInDTO dto) {
        Customer customer = dto.toEntity();
        customerRegistrationService.registerNewCustomer(customer);
        return new CustomerOutDTO(customer);
    }
}
