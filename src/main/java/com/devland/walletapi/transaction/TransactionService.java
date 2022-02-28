package com.devland.walletapi.transaction;

import com.devland.walletapi.wallet.Wallet;
import com.devland.walletapi.wallet.WalletNotFoundException;
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

    public Wallet transfer(Long walletFromId, Long walletToId, Integer amount) {
        Wallet walletFrom = this.walletService.findById(walletFromId);
        Integer updateBalance;
        if (this.walletRepository.findById(walletToId).isEmpty()){
            throw new WalletNotFoundException();
        }
        if (walletFrom.getBalance()==null) {
            throw new TransactionBalanceNotSufficeException();
        } 
        if (walletFrom.getBalance()<amount) {
            throw new TransactionBalanceNotSufficeException();
        } else{
            updateBalance = walletFrom.getBalance()-amount;
        }
        walletFrom.setBalance(updateBalance);
        topUp(walletToId, amount);
        return this.walletRepository.save(walletFrom);
    }
}