package com.devland.walletapi.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionHistoryRequestTransferDTO {
    private Long toWalletId;
    private Integer amount;
    private String note;
}
