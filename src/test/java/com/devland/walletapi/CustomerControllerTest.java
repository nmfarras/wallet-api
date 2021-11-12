package com.devland.walletapi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.devland.walletapi.customer.Customer;
import com.devland.walletapi.customer.CustomerRepository;
import com.devland.walletapi.customer.CustomerResponseDTO;
import com.devland.walletapi.customer.CustomerService;
import com.devland.walletapi.wallet.WalletRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    WalletRepository walletRepository;

    @AfterEach
    void cleanUp(){
        customerRepository.deleteAll();
        walletRepository.deleteAll();
    }

    @Test
    void getCustomers_shouldReturnListOfCustomerResponseDTO_whenInvoked() throws Exception{
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDate birthDate = LocalDate.of(1989, 01, 13);
        Customer paksi = Customer.builder().firstName("firstName").lastName("lastName").nik(3256l).dateOfBirth(birthDate).createdAt(currentDateTime).build();
        Customer savedCustomer = this.customerRepository.save(paksi);

        CustomerResponseDTO savedCustomerResponseDTO = CustomerResponseDTO.builder().id(savedCustomer.getId()).firstName(savedCustomer.getFirstName()).lastName(savedCustomer.getLastName())
                                                  .nik(savedCustomer.getNik()).dateOfBirth(savedCustomer.getDateOfBirth()).createdAt(savedCustomer.getCreatedAt()).build();


        List<CustomerResponseDTO> customerResponseDTOList = List.of(savedCustomerResponseDTO);
        String expectedResult = this.objectMapper.writeValueAsString(customerResponseDTOList);

        RequestBuilder request =MockMvcRequestBuilders.get("/customers");

        this.mockMvc.perform(request)
            .andExpect(MockMvcResultMatchers.content().json(expectedResult));
    }
}
