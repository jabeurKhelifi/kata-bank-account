package com.kata.bank.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;
    private String type;  // "DEPOSIT" or "WITHDRAWAL"
    private BigDecimal amount;
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount;

    public Transaction(LocalDateTime date, String type, BigDecimal amount, BankAccount bankAccount) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.bankAccount = bankAccount;
    }
    public Transaction(LocalDateTime date, String type, BigDecimal amount, BigDecimal balance) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.balance = balance;
    }

   
}
