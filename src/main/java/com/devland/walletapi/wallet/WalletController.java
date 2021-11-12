package com.devland.walletapi.wallet;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
    @Autowired
    private WalletService walletService;
    
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/wallets")
    public ResponseEntity<List<WalletResponseDTO>> getWallet() {
        List<Wallet> walletList = this.walletService.getWallets();

        List<WalletResponseDTO> walletResponseDTOList = walletList.stream().map(
            wallet -> wallet.convertTo(this.modelMapper, WalletResponseDTO.class))
            .collect(Collectors.toList());
        
        return ResponseEntity.status(HttpStatus.OK).body(walletResponseDTOList);    
    }
    
    @PostMapping("/wallets")
    public ResponseEntity<WalletResponseDTO> createWallet(@RequestBody WalletRequestDTO walletRequestDTO){
        Wallet newWallet = Wallet.builder().walletName(walletRequestDTO.getWalletName()).build();
        Wallet saveWallet = this.walletService.createWallet(newWallet);
        WalletResponseDTO walletResponseDTO = WalletResponseDTO.builder().id(saveWallet.getId()).walletName(saveWallet.getWalletName())
                                              .createdAt(saveWallet.getCreatedAt()).balance(saveWallet.getBalance()).build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(walletResponseDTO);
    }
}