package com.devland.walletapi.transaction;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// import com.devland.walletapi.customer.Customer;
import com.devland.walletapi.customer.CustomerService;
import com.devland.walletapi.wallet.Wallet;
import com.devland.walletapi.wallet.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionHistoryService {
    
    @Autowired
    CustomerService customerService;

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    public List<TransactionHistory> getTransactionHistory() {
        return this.transactionHistoryRepository.findAll();
    }

    public List<TransactionHistory> getTransactionHistoryForWallet(Long walletId) {
        Wallet wallet = this.walletService.findById(walletId);
        List<TransactionHistory> fromTransactionHistories = this.transactionHistoryRepository.findByWalletFrom(wallet);
        List<TransactionHistory> toTransactionHistories = this.transactionHistoryRepository.findByWalletTo(wallet);
        return Stream.of(fromTransactionHistories, toTransactionHistories).flatMap(Collection::stream).collect(Collectors.toList());
    }
    
    public TransactionHistory createWalletOpeningTransactionHistory(Wallet wallet){
        Wallet walletFrom = null;
        Wallet walletTo = wallet;
        TransactionHistory newTransactionHistory = TransactionHistory.builder().note("Wallet Opening").transactionType("Top Up")
                                                   .walletFrom(walletFrom).walletTo(walletTo).amount(wallet.getBalance()).createdAt(wallet.getCreatedAt()).build();

        return this.transactionHistoryRepository.save(newTransactionHistory);
    }

    public TransactionHistory createTopUpTransactionHistory(Long toWalletId,TransactionHistoryRequestTransferDTO transactionHistoryRequestDTO){
        Wallet walletFrom = null;
        Wallet walletTo = this.walletService.findById(toWalletId);
        LocalDateTime currentDateTime = LocalDateTime.now();
        TransactionHistory newTransactionHistory = TransactionHistory.builder().note(transactionHistoryRequestDTO.getNote()).transactionType("Top Up")
                                                   .walletFrom(walletFrom).walletTo(walletTo).amount(transactionHistoryRequestDTO.getAmount()).createdAt(currentDateTime).build();
        this.transactionService.topUp(toWalletId, transactionHistoryRequestDTO.getAmount());
        return this.transactionHistoryRepository.save(newTransactionHistory);
    }

    public TransactionHistory createTransferTransactionHistory(Long fromWalletId,TransactionHistoryRequestTransferDTO transactionHistoryRequestDTO){
        Wallet walletFrom = this.walletService.findById(fromWalletId);
        Wallet walletTo = this.walletService.findById(transactionHistoryRequestDTO.getToWalletId());
        LocalDateTime currentDateTime = LocalDateTime.now();
        TransactionHistory newTransactionHistory = TransactionHistory.builder().note(transactionHistoryRequestDTO.getNote()).transactionType("Transfer")
                                                   .walletFrom(walletFrom).walletTo(walletTo).amount(transactionHistoryRequestDTO.getAmount()).createdAt(currentDateTime).build();
        this.transactionService.transfer(fromWalletId, transactionHistoryRequestDTO.getToWalletId(), transactionHistoryRequestDTO.getAmount());
        return this.transactionHistoryRepository.save(newTransactionHistory);
    }
}
