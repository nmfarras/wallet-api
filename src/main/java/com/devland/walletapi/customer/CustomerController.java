package com.devland.walletapi.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponseDTO>> getCustomer() {
        List<Customer> customerList = this.customerService.getCustomers();

        List<CustomerResponseDTO> customerResponseDTOList = customerList.stream().map(
            customer -> new CustomerResponseDTO(
                customer.getId(), customer.getFirstName(), customer.getLastName(),
                customer.getNik(), customer.getDateOfBirth(), customer.getCreatedAt())
            ).collect(Collectors.toList());
        
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTOList);    
    }
    
    @PostMapping("/customers")
    public ResponseEntity<Customer> createStudent(@RequestBody CustomerRequestDTO customerRequestDTO){
        Customer newCustomer = new Customer();
        newCustomer.setFirstName(customerRequestDTO.getFirstName());
        newCustomer.setLastName(customerRequestDTO.getLastName());
        newCustomer.setDateOfBirth(customerRequestDTO.getDateOfBirth());
        newCustomer.setNik(customerRequestDTO.getNik());

        Customer saveCustomer = this.customerService.createCustomer(newCustomer);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveCustomer);
    }
}
