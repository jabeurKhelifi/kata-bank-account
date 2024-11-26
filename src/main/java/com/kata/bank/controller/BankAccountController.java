package com.kata.bank.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kata.bank.service.BankAccountService;
@RestController
@RequestMapping("/api/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping("/{id}/deposit")
    public ResponseEntity<?> deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        bankAccountService.deposit(id, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<?> withdraw(@PathVariable Long id, @RequestParam BigDecimal amount) {
        bankAccountService.withdraw(id, amount);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}/statement")
    public ResponseEntity<List<String>> getAccountStatement(@PathVariable Long id) {
        List<String> statement = bankAccountService.getAccountStatement(id);
        return ResponseEntity.ok(statement);
    }
}