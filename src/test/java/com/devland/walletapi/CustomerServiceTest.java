package com.devland.walletapi;

import java.time.LocalDateTime;

import com.devland.walletapi.customer.Customer;
import com.devland.walletapi.customer.CustomerRepository;
import com.devland.walletapi.customer.CustomerService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerRepository customerRepository;

    @Test
    void getCustomer_shouldReturnAllCustomers_whenInvoked(){
        this.customerService.getCustomers();
        Mockito.verify(this.customerRepository,Mockito.times(1)).findAll();
    }

    @Test
    void createStudent_shouldReturnSaveStudent_whenInvoked(){
        Customer paksi = new Customer();
        LocalDateTime currentDateTime = LocalDateTime.now();
        paksi.setFirstName("Paksi");
        paksi.setLastName("Bumi");
        paksi.setId(1l);
        paksi.setNik(32511234l);
        paksi.setCreatedAt(currentDateTime);

        // List<Student> students = List.of(paksi);
        Mockito.when(this.customerRepository.save(paksi)).thenReturn(paksi);
        this.customerService.createCustomer(paksi);
        Mockito.verify(this.customerRepository,Mockito.times(1)).save(paksi);
    }
}
