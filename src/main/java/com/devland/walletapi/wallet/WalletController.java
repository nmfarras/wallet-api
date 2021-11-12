package com.devland.walletapi.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalletController {
    @Autowired
    private WalletService walletService;
    
    // @Autowired
    // private ModelMapper modelMapper;
}