package com.devland.walletapi.customer;

import java.util.List;
import java.util.stream.Collectors;

import com.devland.walletapi.transaction.TransactionHistoryRequestTransferDTO;
import com.devland.walletapi.transaction.TransactionHistoryService;
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
    private TransactionHistoryService transactionHistoryService;

    @Autowired
    private WalletService walletService;
    
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
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO){
        Customer newCustomer = Customer.builder().firstName(customerRequestDTO.getFirstName()).lastName(customerRequestDTO.getLastName())
                               .dateOfBirth(customerRequestDTO.getDateOfBirth()).nik(customerRequestDTO.getNik()).createdAt(customerRequestDTO.getCreatedAt()).build();
        Customer saveCustomer = this.customerService.createCustomer(newCustomer);
        Wallet newWallet = this.customerService.createCustomerWallet(saveCustomer.getId(), WalletRequestDTO.builder().walletName("Main").balance(50000).build());
        this.transactionHistoryService.createWalletOpeningTransactionHistory(newWallet);
        Wallet updateWallet = this.walletService.findById(newWallet.getWalletId());
        List<Wallet> updateWalletList = List.of(updateWallet);
        CustomerResponseDTO customerResponseDTO = CustomerResponseDTO.builder().id(saveCustomer.getId()).firstName(saveCustomer.getFirstName()).lastName(saveCustomer.getLastName())
                                                  .dateOfBirth(saveCustomer.getDateOfBirth()).nik(saveCustomer.getNik()).createdAt(saveCustomer.getCreatedAt())
                                                  .walletList(updateWalletList).build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDTO);
    }

    @PostMapping("/customers/{customerId}/add-wallet")
    public ResponseEntity<WalletResponseDTO> createCustomerWallet(@PathVariable("customerId") Long customerId,@RequestBody WalletRequestDTO walletRequestDTO){
        Wallet wallet = this.customerService.createCustomerWallet(customerId, walletRequestDTO);
        this.transactionHistoryService.createWalletOpeningTransactionHistory(wallet);
        WalletResponseDTO walletResponseDTO = WalletResponseDTO.builder().id(wallet.getWalletId()).walletName(wallet.getWalletName()).balance(wallet.getBalance())
                                                                         .createdAt(wallet.getCreatedAt()).build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(walletResponseDTO);
    }

    @PostMapping("/customers/{customerId}/top-up")
    public ResponseEntity<Void> topUpWallet(@PathVariable("customerId") Long customerId,@RequestBody TransactionHistoryRequestTransferDTO transactionHistoryRequestDTO){
        Wallet wallet= this.walletService.findById(transactionHistoryRequestDTO.getToWalletId());
        this.transactionHistoryService.createTopUpTransactionHistory(wallet.getWalletId(), transactionHistoryRequestDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/customers/{customerId}/transfer/{walletId}")
    public ResponseEntity<Void> tansferWallet(@PathVariable("customerId") Long customerId, @PathVariable("walletId") Long fromWalletId, @RequestBody TransactionHistoryRequestTransferDTO transactionHistoryRequestDTO){
        this.transactionHistoryService.createTransferTransactionHistory(fromWalletId, transactionHistoryRequestDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
