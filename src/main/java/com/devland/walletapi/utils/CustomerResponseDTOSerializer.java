package com.devland.walletapi.utils;

import java.io.IOException;
// import java.util.List;
import java.util.List;
import java.util.stream.Collectors;

import com.devland.walletapi.customer.CustomerResponseDTO;
import com.devland.walletapi.transaction.TransactionHistory;
import com.devland.walletapi.transaction.TransactionHistoryResponseDTO;
import com.devland.walletapi.transaction.TransactionHistoryService;
// import com.devland.walletapi.transaction.TransactionHistory;
import com.devland.walletapi.wallet.Wallet;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerResponseDTOSerializer extends StdSerializer<CustomerResponseDTO> {
    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private ModelMapper modelMapper;
    
    public CustomerResponseDTOSerializer() {
        super(CustomerResponseDTO.class);
    }

    @Override
    public void serialize(CustomerResponseDTO value, JsonGenerator gen, SerializerProvider provider)
            throws IOException {

        gen.writeStartObject();
        gen.writeNumberField("customerId", value.getId());
        gen.writeStringField("name", value.getFirstName() + " " + value.getLastName());
        gen.writeNumberField("nik", value.getNik());
        gen.writeObjectField("dateOfBirth", value.getDateOfBirth());
        gen.writeObjectField("registerAt", value.getCreatedAt());
        // gen.writeObjectField("walletList",value.getWalletList());

        gen.writeArrayFieldStart("walletList");

        for( Wallet walletList: value.getWalletList() ) {
            gen.writeStartObject();
            gen.writeNumberField("walletId",walletList.getWalletId());
            gen.writeStringField("walletName",walletList.getWalletName());
            gen.writeNumberField("ballance",walletList.getBalance());
            gen.writeObjectField("createdAt", walletList.getCreatedAt());

            List<TransactionHistory> transaction = this.transactionHistoryService.getTransactionHistoryForWallet(walletList.getWalletId());
            List<TransactionHistoryResponseDTO> transactionResponseDTOList = transaction.stream().map(
            transactionList -> transactionList.convertTo(this.modelMapper, TransactionHistoryResponseDTO.class))
            .sorted((o1, o2)->o1.getId().
                    compareTo(o2.getId()))
            .collect(Collectors.toList());

            gen.writeObjectField("transactionHistory", transactionResponseDTOList);

            // gen.writeObjectField("fromWallet", walletList.getTransactionFrom());

            // gen.writeArrayFieldStart("transactionHistories");
            // for( TransactionHistory transaction: walletList.) {
            //     gen.writeStartObject();
                
            //     gen.writeEndObject();
            // }
            // gen.writeEndArray();

            gen.writeEndObject();
        }

        gen.writeEndArray();

        // provider.defaultSerializeField("customerId", value.getCustomer().getId(),
        // gen);
        // provider.defaultSerializeField("details", value, gen);

        gen.writeEndObject();

    }
}
