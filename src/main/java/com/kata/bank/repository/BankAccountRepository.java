package com.kata.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kata.bank.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    BankAccount findById(long id);
}

