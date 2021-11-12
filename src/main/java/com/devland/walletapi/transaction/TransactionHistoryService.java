package com.devland.walletapi.transaction;

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
    
    // public void transaction(String transactionType, String note, Integer amount) {
    //     Wallet wallet = this.walletService.findById(walletId);

    //     wallet.setBalance(balance);
    // }

    public void topUp(Long walletId, int balance) {
        Wallet wallet = this.transactionService.topUp(walletId, balance);
        wallet.setBalance(balance);
    }
}
