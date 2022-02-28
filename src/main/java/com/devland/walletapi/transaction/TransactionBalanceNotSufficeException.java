package com.devland.walletapi.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Balance not suffice")
public class TransactionBalanceNotSufficeException extends RuntimeException {

}
