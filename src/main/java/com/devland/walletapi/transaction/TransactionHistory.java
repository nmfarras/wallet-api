package com.devland.walletapi.transaction;

// import java.time.LocalDateTime;
// import java.util.List;

import javax.persistence.Entity;
// import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
// import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
// import javax.persistence.OneToMany;

// import com.devland.walletapi.customer.Customer;
import com.devland.walletapi.wallet.Wallet;
import com.fasterxml.jackson.annotation.JsonBackReference;
// import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// import net.bytebuddy.dynamic.TypeResolutionStrategy.Lazy;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    @Nullable
    private Wallet wallet;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
