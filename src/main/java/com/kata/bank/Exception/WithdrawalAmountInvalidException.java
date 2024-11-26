package com.kata.bank.Exception;

public class WithdrawalAmountInvalidException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public WithdrawalAmountInvalidException(String message) {
        super(message);
    }
}
