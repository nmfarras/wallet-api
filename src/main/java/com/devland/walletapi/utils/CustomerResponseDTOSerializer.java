package com.devland.walletapi.utils;

import java.io.IOException;
// import java.util.List;

import com.devland.walletapi.customer.CustomerResponseDTO;
import com.devland.walletapi.wallet.Wallet;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class CustomerResponseDTOSerializer extends StdSerializer<CustomerResponseDTO> {
    
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
            gen.writeObject(walletList.getWalletId());
            gen.writeObject(walletList.getWalletName());
        }

        gen.writeEndArray();

        // provider.defaultSerializeField("customerId", value.getCustomer().getId(),
        // gen);
        // provider.defaultSerializeField("details", value, gen);

        gen.writeEndObject();

    }
}
