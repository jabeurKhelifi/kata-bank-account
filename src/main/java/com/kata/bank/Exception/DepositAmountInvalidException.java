package com.kata.bank.Exception;

public class DepositAmountInvalidException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public DepositAmountInvalidException(String message) {
        super(message);
    }
}
