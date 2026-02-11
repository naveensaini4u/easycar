package com.naveen.easycar.service;

import com.naveen.easycar.dto.CustomerRequestDto;
import com.naveen.easycar.entity.Customer;
import com.naveen.easycar.exception.BookingConflictException;
import com.naveen.easycar.exception.EntityNotFoundException;
import com.naveen.easycar.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer registerCustomer(Customer customer) {

        customerRepository.findByEmail(customer.getEmail())
                .ifPresent(c -> {
                    throw new BookingConflictException(
                            "Email already registered"
                    );
                });

        customerRepository.findByPhone(customer.getPhone())
                .ifPresent(c -> {
                    throw new BookingConflictException(
                            "Phone already registered"
                    );
                });

        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Long id, CustomerRequestDto dto) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Customer not found")
                );

        customerRepository.findByEmail(dto.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(c -> {
                    throw new BookingConflictException("Email already in use");
                });

        customerRepository.findByPhone(dto.getPhone())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(c -> {
                    throw new BookingConflictException("Phone already in use");
                });

        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        customer.setDocumentId(dto.getDocumentId());

        return customerRepository.save(customer);
    }


    public Customer getCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Customer not found")
                );
    }
}

