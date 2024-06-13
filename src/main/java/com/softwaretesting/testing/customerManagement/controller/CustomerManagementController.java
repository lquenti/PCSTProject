package com.softwaretesting.testing.customerManagement.controller;

import com.softwaretesting.testing.customerManagement.service.CustomerManagementService;
import com.softwaretesting.testing.model.Customer;
import com.softwaretesting.testing.dto.inbound.CustomerInDTO;
import com.softwaretesting.testing.dto.outbound.CustomerOutDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerManagementController {

    @Autowired
    private CustomerManagementService customerManagementService;

    public CustomerManagementController() {}

    @GetMapping("list")
    public List<CustomerOutDTO> list() {
        Collection<Customer> customersIterable = customerManagementService.list();
        List<CustomerOutDTO> outDTOs = new ArrayList<>();
        customersIterable.forEach(entry -> outDTOs.add(new CustomerOutDTO(entry)));
        return outDTOs;
    }

    @GetMapping("{cid}")
    public CustomerOutDTO getById(@PathVariable("cid") Long id) {
        Customer customer = customerManagementService.findById(id);
        return new CustomerOutDTO(customer);
    }

    @PostMapping
    public CustomerOutDTO addCustomer(@RequestBody CustomerInDTO dto) {
        Customer customer = dto.toEntity();
        customerManagementService.addCustomer(customer);
        return new CustomerOutDTO(customer);
    }

    @DeleteMapping("{cid}")
    public ResponseEntity<?> delete(@PathVariable("cid") Long id) {
        customerManagementService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
