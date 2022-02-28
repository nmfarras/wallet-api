package com.devland.walletapi.transaction;

import java.time.LocalDateTime;

import com.devland.walletapi.wallet.Wallet;
import com.devland.walletapi.wallet.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionHistoryService {
    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionService transactionService;
    
    // public void transaction(String transactionType, String note, Integer amount) {
    //     Wallet wallet = this.walletService.findById(walletId);

    //     wallet.setBalance(balance);
    // }
    private TransactionHistory transactionBuildHistory(Integer amount, Wallet wallet, String transactionType) {
        // Book book = this.bookService.findById(bookBorrowingHistoryRequestDTO.getBookId());
        // LocalDateTime currentDateTime = LocalDateTime.now();
        
        // BookBorrowingHistory newBookBorrowingHistory = BookBorrowingHistory.builder().student(student).book(book).borrowedAt(currentDateTime).build();
        // this.bookService.bookBorrows(book);

        // this.bookBorrowingHistoryRepository.save(newBookBorrowingHistory);
        // Wallet updateWallet = this.walletService.findById(wallet.get)
        Wallet updateWallet = this.walletService.findById(wallet.getId());
        LocalDateTime currentDateTime = LocalDateTime.now();

        TransactionHistory newTransactionHistory = TransactionHistory.builder().wallet(wallet).build();

        return this.transactionHistoryRepository.save(newTransactionHistory);
    }

    public TransactionHistory topUpTransaction(Long walletId, Integer amount) {
        Wallet wallet = this.transactionService.topUp(walletId, amount);
        return transactionBuildHistory(amount, wallet, "Top Up");
    }

    public TransactionHistory transferTransaction(Long walletId, Integer amount) {
        Wallet wallet = this.transactionService.transfer(walletId, amount);
        return transactionBuildHistory(amount, wallet, "Transfer");
    }

    public TransactionHistory receiveTransaction(Long walletId, Integer amount) {
        Wallet wallet = this.transactionService.topUp(walletId, amount);
        return transactionBuildHistory(amount, wallet, "Receive");
    }
}
