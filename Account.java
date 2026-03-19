package com.bank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private double balance;

    public Account() {}

    public Account(double balance) {
        this.balance = balance;
    }

}