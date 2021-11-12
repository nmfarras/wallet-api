package com.devland.walletapi.wallet;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such wallet exist with the provided id")
public class WalletNotFoundException extends RuntimeException {

}
