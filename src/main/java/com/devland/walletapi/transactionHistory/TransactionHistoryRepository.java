package com.devland.walletapi.transactionHistory;

// import com.devland.walletapi.customer.Customer;
// import com.devland.walletapi.wallet.Wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory,Long> {
    // BookBorrowingHistory findByBookAndStudent(Book book, Student student);
    // TransactionHistory findByCustomer(Customer customer);
    // TransactionHistory findByCustomerAndWallet(Customer customer, Wallet wallet);
}
