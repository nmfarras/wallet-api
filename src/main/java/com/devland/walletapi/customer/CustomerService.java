package com.devland.walletapi.customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.devland.walletapi.wallet.Wallet;
import com.devland.walletapi.wallet.WalletRequestDTO;
import com.devland.walletapi.wallet.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private WalletService walletService;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        Optional<Customer> existingCustomer = this.customerRepository.findById(id);

        if(existingCustomer.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        return this.customerRepository.getById(id);
    }

    public Customer createCustomer(Customer customer) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        customer.setCreatedAt(currentDateTime);

        return this.customerRepository.save(customer);
    }

    public Wallet createCustomerWallet(Long customerId, WalletRequestDTO walletRequestDTO) {
        Customer customer = findById(customerId);
        Wallet wallet = this.walletService.createCustomerWallet(customer, walletRequestDTO);
        return wallet;
    }
}
