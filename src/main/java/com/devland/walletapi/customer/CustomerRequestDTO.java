package com.devland.walletapi.customer;

import java.time.LocalDate;
// import java.time.LocalDateTime;
import java.time.LocalDateTime;

// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequestDTO {
    private String firstName;
    private String lastName;
    private Long nik;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
}
