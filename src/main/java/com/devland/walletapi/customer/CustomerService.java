package com.devland.walletapi.customer;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        customer.setCreatedAt(currentDateTime);

        return this.customerRepository.save(customer);
    }
}
