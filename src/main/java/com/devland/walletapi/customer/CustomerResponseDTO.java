package com.devland.walletapi.customer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.devland.walletapi.utils.CustomerResponseDTOSerializer;

// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;

import com.devland.walletapi.wallet.Wallet;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using = CustomerResponseDTOSerializer.class)
@Builder
public class CustomerResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Long nik;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    @JsonIgnore
    private List<Wallet> walletList;
}
