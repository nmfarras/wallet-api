package com.devland.walletapi.transaction;

import com.devland.walletapi.wallet.Wallet;
import com.devland.walletapi.wallet.WalletRepository;
import com.devland.walletapi.wallet.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    @Autowired
    WalletService walletService;

    @Autowired
    WalletRepository walletRepository;
    
    public Wallet topUp(Long walletId, Integer amount) {
        Wallet wallet = this.walletService.findById(walletId);
        Integer updateBalance;
        if (wallet.getBalance()==null) {
            updateBalance = amount;
        } else{
            updateBalance = wallet.getBalance()+amount;
        }
        wallet.setBalance(updateBalance);

        return this.walletRepository.save(wallet);
    }
}