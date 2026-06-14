package com.to4ilochka.rental.service;

import com.to4ilochka.rental.dto.CustomerDto.*;
import com.to4ilochka.rental.entity.BookingStatus;
import com.to4ilochka.rental.entity.Customer;
import com.to4ilochka.rental.exception.ConflictOperationException;
import com.to4ilochka.rental.exception.DuplicateResourceException;
import com.to4ilochka.rental.exception.ResourceNotFoundException;
import com.to4ilochka.rental.repository.BookingRepository;
import com.to4ilochka.rental.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;

    public CustomerResponse createCustomer(CustomerCreateRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Customer with email " + request.getEmail() + " already exists");
        }

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();

        return mapToResponse(customerRepository.save(customer));
    }

    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = getCustomerEntity(id);
        return mapToResponse(customer);
    }

    public CustomerResponse updateCustomer(Long id, CustomerUpdateRequest request) {
        Customer customer = getCustomerEntity(id);

        if (request.getEmail() != null && !request.getEmail().equals(customer.getEmail())) {
            if (customerRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException("Customer with email " + request.getEmail() + " already exists");
            }
            customer.setEmail(request.getEmail());
        }

        if (request.getFirstName() != null) customer.setFirstName(request.getFirstName());
        if (request.getLastName() != null) customer.setLastName(request.getLastName());
        if (request.getPhone() != null) customer.setPhone(request.getPhone());

        return mapToResponse(customerRepository.save(customer));
    }

    public void deleteCustomer(Long id) {
        Customer customer = getCustomerEntity(id);

        if (bookingRepository.existsByCustomerIdAndStatus(id, BookingStatus.ACTIVE)) {
            throw new ConflictOperationException("Cannot delete customer with active bookings");
        }

        customerRepository.delete(customer);
    }

    public List<CustomerResponse> searchCustomers(String query) {
        String lowerQuery = query.toLowerCase();
        return customerRepository.findAll().stream()
                .filter(customer -> customer.getFirstName().toLowerCase().contains(lowerQuery)
                        || customer.getLastName().toLowerCase().contains(lowerQuery)
                        || customer.getEmail().toLowerCase().contains(lowerQuery))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public long getTotalCount() {
        return customerRepository.count();
    }

    public Customer getCustomerEntity(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .build();
    }
}
