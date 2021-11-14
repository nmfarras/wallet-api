package com.devland.walletapi.wallet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.devland.walletapi.customer.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    @Autowired
    private WalletRepository walletRepository;

    public List<Wallet> getWallets() {
        return this.walletRepository.findAll();
    }

    public List<Wallet> getWalletsByCustomer(Customer customer) {
        return this.walletRepository.findByCustomer(customer);
    }

    public Wallet findById(Long walletId) {
        Optional<Wallet> wallet = this.walletRepository.findById(walletId);

        if(wallet.isEmpty()) {
            throw new WalletNotFoundException();
        }

        return wallet.get();
    }

    public Wallet createCustomerWallet(Customer customer, WalletRequestDTO walletRequestDTO) {
        Wallet customerWallet = Wallet.builder().walletName(walletRequestDTO.getWalletName()).build();

        LocalDateTime currentDateTime = LocalDateTime.now();
        customerWallet.setCreatedAt(currentDateTime);
        customerWallet.setCustomer(customer);
        customerWallet.setBalance(walletRequestDTO.getBalance());

        return this.walletRepository.save(customerWallet);
    }

    public Wallet createWallet(Wallet wallet) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        wallet.setCreatedAt(currentDateTime);

        return this.walletRepository.save(wallet);
    }
}
