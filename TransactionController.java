package com.bank.controller;

import com.bank.dto.TransactionRequest;
import com.bank.model.Transaction;
import com.bank.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {

    @Autowired
    private AccountService service;

    //  Deposit API
    @PostMapping("/deposit")
    public Transaction deposit(@RequestBody TransactionRequest req) {
        return service.deposit(req);
    }

    //  Withdraw API
    @PostMapping("/withdraw")
    public Transaction withdraw(@RequestBody TransactionRequest req) {
        return service.withdraw(req);
    }

    //  Memo (all transactions)
    @GetMapping("/transactions")
    public List<Transaction> getTransactions() {
        return service.getTransactions();
    }

    //  Balance
    @GetMapping("/balance")
    public double getBalance() {
        return service.getBalance();
    }
}
