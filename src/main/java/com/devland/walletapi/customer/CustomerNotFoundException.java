package com.devland.walletapi.customer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such customer exist with the provided id")
public class CustomerNotFoundException extends RuntimeException{

}
