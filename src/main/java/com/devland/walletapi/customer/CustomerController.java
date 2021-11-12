package com.devland.walletapi.customer;

import java.util.List;
import java.util.stream.Collectors;

import com.devland.walletapi.wallet.Wallet;
import com.devland.walletapi.wallet.WalletRequestDTO;
import com.devland.walletapi.wallet.WalletResponseDTO;
import com.devland.walletapi.wallet.WalletService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerResponseDTO>> getCustomer() {
        List<Customer> customerList = this.customerService.getCustomers();

        // List<CustomerResponseDTO> customerResponseDTOList = customerList.stream().map(
        //     customer -> new CustomerResponseDTO(
        //         customer.getId(), customer.getFirstName(), customer.getLastName(),
        //         customer.getNik(), customer.getDateOfBirth(), customer.getCreatedAt())
        //     ).collect(Collectors.toList());

        List<CustomerResponseDTO> customerResponseDTOList = customerList.stream().map(
            customer -> customer.convertTo(this.modelMapper, CustomerResponseDTO.class))
            .collect(Collectors.toList());
        
        return ResponseEntity.status(HttpStatus.OK).body(customerResponseDTOList);    
    }
    
    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO){
        Customer newCustomer = new Customer();
        newCustomer.setFirstName(customerRequestDTO.getFirstName());
        newCustomer.setLastName(customerRequestDTO.getLastName());
        newCustomer.setDateOfBirth(customerRequestDTO.getDateOfBirth());
        newCustomer.setNik(customerRequestDTO.getNik());

        Customer saveCustomer = this.customerService.createCustomer(newCustomer);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveCustomer);
    }

    @PostMapping("/customers/{customerId}/add-wallet")
    public ResponseEntity<WalletResponseDTO> createCustomerWallet(@PathVariable("customerId") Long customerId,@RequestBody WalletRequestDTO walletRequestDTO){
        Wallet wallet= this.customerService.createCustomerWallet(customerId, walletRequestDTO);
        WalletResponseDTO walletResponseDTO = WalletResponseDTO.builder().id(wallet.getId()).walletName(wallet.getWalletName()).balance(wallet.getBalance())
                                                                         .customerName(wallet.getCustomer().getFirstName()+wallet.getCustomer().getLastName())
                                                                         .createdAt(wallet.getCreatedAt()).build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(walletResponseDTO);
    }
}
