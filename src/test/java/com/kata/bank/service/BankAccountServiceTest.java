package com.kata.bank.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kata.bank.Exception.DepositAmountInvalidException;
import com.kata.bank.Exception.InsufficientFundsException;
import com.kata.bank.Exception.WithdrawalAmountInvalidException;
import com.kata.bank.entity.BankAccount;
import com.kata.bank.entity.Transaction;
import com.kata.bank.repository.BankAccountRepository;
import com.kata.bank.service.impl.BankAccountServiceImpl;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private BankAccount account;
    private List<Transaction> transactions;

    @BeforeEach
    public void setUp() {
    	// Create a list of 3 transactions
        transactions = new ArrayList<>();

        // Add a deposit transaction
        transactions.add(new Transaction(LocalDateTime.now(), "DEPOSIT", BigDecimal.valueOf(500), BigDecimal.valueOf(1500)));

        // Add a withdrawal transaction
        transactions.add(new Transaction(LocalDateTime.now(), "WITHDRAWAL", BigDecimal.valueOf(200), BigDecimal.valueOf(1300)));

        // Add another deposit transaction
        transactions.add(new Transaction(LocalDateTime.now(), "DEPOSIT", BigDecimal.valueOf(300), BigDecimal.valueOf(1600)));

        account = new BankAccount(1L, BigDecimal.valueOf(1000), transactions);
    }

    @Test
    public void testDeposit() {
        // When
        BigDecimal depositAmount = BigDecimal.valueOf(500);

        when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(account));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(account);

        // Then
        bankAccountService.deposit(1L, depositAmount);

        // Assert
        assertEquals(BigDecimal.valueOf(1500), account.getBalance(), "Balance should be updated after deposit");
    }

    @Test
    public void testDeposit_InvalidAmount() {
        // When
        BigDecimal depositAmount = BigDecimal.valueOf(-500);

        // Then
        DepositAmountInvalidException exception = assertThrows(DepositAmountInvalidException.class, () -> bankAccountService.deposit(1L, depositAmount), "Deposit amount must be positive"
        );

        // Assert
        assertEquals("Deposit amount must be positive", exception.getMessage());
    }

    @Test
    public void testWithdraw() {
        // When
        BigDecimal withdrawAmount = BigDecimal.valueOf(200);

        when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(account));
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(account);

        // Then
        bankAccountService.withdraw(1L, withdrawAmount);

        // Assert
        assertEquals(BigDecimal.valueOf(800), account.getBalance(), "Balance should be updated after withdrawal");
    }

    @Test
    public void testWithdraw_InsufficientFunds() {
        // When
        BigDecimal withdrawAmount = BigDecimal.valueOf(1200); // More than available balance

        when(bankAccountRepository.findById(any(Long.class))).thenReturn(Optional.of(account));

        // Then
        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> bankAccountService.withdraw(1L, withdrawAmount), "Insufficient funds for withdrawal"
        );

        // Assert
        assertEquals("Insufficient funds for withdrawal", exception.getMessage());
    }

    @Test
    public void testWithdraw_InvalidAmount() {
        // When
        BigDecimal withdrawAmount = BigDecimal.valueOf(-200); // Negative withdrawal amount


        // Then
        WithdrawalAmountInvalidException exception = assertThrows(WithdrawalAmountInvalidException.class, () -> bankAccountService.withdraw(1L, withdrawAmount), "Withdrawal amount must be positive"
        );

        // Assert
        assertEquals("Withdrawal amount must be positive", exception.getMessage());
    }
}

