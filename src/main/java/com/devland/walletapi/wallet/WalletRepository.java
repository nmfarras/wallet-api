package com.devland.walletapi.wallet;

import java.util.List;

import com.devland.walletapi.customer.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
    List<Wallet> findByCustomerAndWalletId(Customer customer, Long walletId);
    List<Wallet> findByCustomer(Customer customer);
}
