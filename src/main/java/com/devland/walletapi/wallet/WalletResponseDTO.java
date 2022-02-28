package com.devland.walletapi.wallet;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletResponseDTO {
    private long id;
    private String walletName;
    private Integer balance;
    private LocalDateTime createdAt;
}
