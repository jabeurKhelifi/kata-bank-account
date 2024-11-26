package com.kata.bank.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kata.bank.Exception.DepositAmountInvalidException;
import com.kata.bank.Exception.InsufficientFundsException;
import com.kata.bank.Exception.WithdrawalAmountInvalidException;
import com.kata.bank.entity.BankAccount;
import com.kata.bank.entity.Transaction;
import com.kata.bank.repository.BankAccountRepository;
import com.kata.bank.service.BankAccountService;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public void deposit(Long accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DepositAmountInvalidException("Deposit amount must be positive");
        }

        BankAccount account = bankAccountRepository.findById(accountId).orElse(null);
        if (account != null) {
        	//set new balance
            account.setBalance(account.getBalance().add(amount));
            Transaction transaction = new Transaction(LocalDateTime.now(), "DEPOSIT", amount, account);
            transaction.setBalance(account.getBalance());
            //add deposit transaction to the bank account history 
            account.getTransactions().add(transaction);
            bankAccountRepository.save(account);
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }

    @Override
    public void withdraw(Long accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new WithdrawalAmountInvalidException("Withdrawal amount must be positive");
        }

        BankAccount account = bankAccountRepository.findById(accountId).orElse(null);
        if (account != null) {
            if (account.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds for withdrawal");
            }
            //set new balance
            account.setBalance(account.getBalance().subtract(amount));
            Transaction transaction = new Transaction(LocalDateTime.now(), "WITHDRAWAL", amount, account);
            transaction.setBalance(account.getBalance());
          //add withdrawal transaction to the bank account history 
            account.getTransactions().add(transaction);
            bankAccountRepository.save(account);
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }

    @Override
    public List<String> getAccountStatement(Long accountId) {
        BankAccount account = bankAccountRepository.findById(accountId).orElse(null);
        List<String> statement = new ArrayList<>();
        if (account != null) {
            statement.add(" DATE                     | TYPE      | AMOUNT  | BALANCE");
            for (Transaction transaction : account.getTransactions()) {
                statement.add(String.format("%-20s| %-10s| %-8s| %-8s", transaction.getDate(), transaction.getType(),
                        transaction.getAmount(), transaction.getBalance()));
            }
        } else {
            throw new IllegalArgumentException("Account not found");
        }
        return statement;
    }

    @Override
    public BankAccount getAccount(Long accountId) {
        return bankAccountRepository.findById(accountId).orElse(null);
    }
}
