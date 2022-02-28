package com.devland.walletapi.transaction;

import java.util.List;

// import com.devland.walletapi.customer.Customer;
import com.devland.walletapi.wallet.Wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory,Long> {
    List<TransactionHistory> findByWalletFrom(Wallet walletFrom);
    List<TransactionHistory> findByWalletTo(Wallet walletTo);
    // List<TransactionHistory> findByCustomer(Customer customer);
    // List<TransactionHistory> findByCustomerAndWallet(Customer customer, Wallet wallet);
}
