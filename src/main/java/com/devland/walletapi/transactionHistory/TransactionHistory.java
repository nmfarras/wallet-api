package com.devland.walletapi.transactionHistory;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

// import com.devland.walletapi.customer.Customer;
import com.devland.walletapi.wallet.Wallet;
import com.fasterxml.jackson.annotation.JsonBackReference;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionType;
    private String note;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    @Nullable
    private Wallet fromWallet;
    // private Wallet toWallet;

    private Integer amount;
    private LocalDateTime createdAt;
}
