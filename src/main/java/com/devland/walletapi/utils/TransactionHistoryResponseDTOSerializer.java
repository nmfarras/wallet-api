package com.devland.walletapi.utils;

import java.io.IOException;

import com.devland.walletapi.transaction.TransactionHistoryResponseDTO;
// import com.devland.walletapi.wallet.Wallet;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class TransactionHistoryResponseDTOSerializer extends StdSerializer<TransactionHistoryResponseDTO> {

    public TransactionHistoryResponseDTOSerializer() {
        super(TransactionHistoryResponseDTO.class);
    }

    @Override
    public void serialize(TransactionHistoryResponseDTO value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {
        // simplify field wallet from and to become Id and customer
        gen.writeStartObject();

        gen.writeNumberField("transactionId", value.getId());
        gen.writeStringField("type", value.getTransactionType());
        gen.writeStringField("note", value.getNote());
        if (value.getWalletFrom()==null) {
            gen.writeObjectField("walletFromId", "-");
        } else{
            gen.writeObjectField("walletFromId", value.getWalletFrom().getWalletId());
        }
        gen.writeObjectField("walletToId", value.getWalletTo().getWalletId());
        gen.writeNumberField("amount", value.getAmount());
        gen.writeObjectField("createdAt", value.getCreatedAt());

        gen.writeEndObject();

    }
}
