package com.bank.dto;

import lombok.Getter;

@Getter
public class TransactionRequest {

    private double amount;
    private String description;

}