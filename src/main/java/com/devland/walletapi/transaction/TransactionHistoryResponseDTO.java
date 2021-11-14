package com.devland.walletapi.transaction;

import java.time.LocalDateTime;

import com.devland.walletapi.utils.TransactionHistoryResponseDTOSerializer;
import com.devland.walletapi.wallet.Wallet;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonSerialize(using = TransactionHistoryResponseDTOSerializer.class)
public class TransactionHistoryResponseDTO {
    private Long id;
    private String transactionType;
    private String note;
    private Wallet walletFrom;
    private Wallet walletTo;
    private Integer amount;
    private LocalDateTime createdAt;
}
