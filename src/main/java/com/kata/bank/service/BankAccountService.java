package com.kata.bank.service;

import java.math.BigDecimal;
import java.util.List;

import com.kata.bank.entity.BankAccount;

public interface BankAccountService {
    void deposit(Long accountId, BigDecimal amount);
    void withdraw(Long accountId, BigDecimal amount);
    List<String> getAccountStatement(Long accountId);
    BankAccount getAccount(Long accountId);
}

