package com.kata.bank.Exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.kata.bank.Exception.DepositAmountInvalidException;
import com.kata.bank.Exception.InsufficientFundsException;
import com.kata.bank.Exception.WithdrawalAmountInvalidException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle DepositAmountInvalidException
    @ExceptionHandler(DepositAmountInvalidException.class)
    public ResponseEntity<String> handleDepositAmountInvalidException(DepositAmountInvalidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Handle WithdrawalAmountInvalidException
    @ExceptionHandler(WithdrawalAmountInvalidException.class)
    public ResponseEntity<String> handleWithdrawalAmountInvalidException(WithdrawalAmountInvalidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Handle InsufficientFundsException
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<String> handleInsufficientFundsException(InsufficientFundsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Handle other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}
