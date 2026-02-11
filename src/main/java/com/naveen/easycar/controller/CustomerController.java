package com.naveen.easycar.controller;

import com.naveen.easycar.dto.CustomerRequestDto;
import com.naveen.easycar.dto.CustomerResponseDto;
import com.naveen.easycar.entity.Customer;
import com.naveen.easycar.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> registerCustomer(
            @Valid @RequestBody CustomerRequestDto dto
    ) {

        Customer customer = new Customer();
        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setDocumentId(dto.getDocumentId());

        Customer saved = customerService.registerCustomer(customer);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapToResponse(saved));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomer(
            @PathVariable Long id
    ) {

        Customer customer = customerService.getCustomer(id);
        return ResponseEntity.ok(mapToResponse(customer));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequestDto dto
    ) {

        Customer updated = customerService.updateCustomer(id, dto);
        return ResponseEntity.ok(mapToResponse(updated));
    }

    private CustomerResponseDto mapToResponse(Customer customer) {

        CustomerResponseDto dto = new CustomerResponseDto();
        dto.setId(customer.getId());
        dto.setFullName(customer.getFullName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setDocumentId(customer.getDocumentId());

        return dto;
    }
}

