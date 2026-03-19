package com.bank.service;

import com.bank.dto.TransactionRequest;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private TransactionRepository transactionRepo;

    private Account getAccount() {
        return accountRepo.findAll().stream().findFirst()
                .orElseGet(() -> accountRepo.save(new Account(0)));
    }

    // ✅ Deposit
    public Transaction deposit(TransactionRequest req) {
        Account acc = getAccount();
        acc.setBalance(acc.getBalance() + req.getAmount());
        accountRepo.save(acc);

        Transaction tx = new Transaction("DEPOSIT", req.getAmount(), req.getDescription());
        return transactionRepo.save(tx);
    }

    // ✅ Withdraw
    public Transaction withdraw(TransactionRequest req) {
        Account acc = getAccount();

        if (acc.getBalance() < req.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        acc.setBalance(acc.getBalance() - req.getAmount());
        accountRepo.save(acc);

        Transaction tx = new Transaction("WITHDRAW", req.getAmount(), req.getDescription());
        return transactionRepo.save(tx);
    }

    // ✅ Get Memo (all transactions)
    public List<Transaction> getTransactions() {
        return transactionRepo.findAll();
    }

    // ✅ Get Balance
    public double getBalance() {
        return getAccount().getBalance();
    }
}