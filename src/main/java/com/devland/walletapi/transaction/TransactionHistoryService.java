package com.devland.walletapi.transaction;

import java.time.LocalDateTime;

import com.devland.walletapi.wallet.Wallet;
import com.devland.walletapi.wallet.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionHistoryService {
    @Autowired
    WalletService walletService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

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
}
