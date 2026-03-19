package com.bank.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String type; // DEPOSIT or WITHDRAW
    @Getter
    private double amount;
    @Getter
    private String description;
    @Getter
    private LocalDateTime dateTime;

    public Transaction() {}

    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.dateTime = LocalDateTime.now();
    }

}