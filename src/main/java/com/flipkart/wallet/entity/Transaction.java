package com.flipkart.wallet.entity;

import com.flipkart.wallet.service.PaymentMethod;
import com.flipkart.wallet.service.TransactionType;

import java.util.Date;

public class Transaction {
    private String userId;
    private double amount;
    private TransactionType type;
    private PaymentMethod method;
    private Date timestamp;

    public Transaction(String userId, double amount, TransactionType type, PaymentMethod method) {
        this.userId = userId;
        this.amount = amount;
        this.type = type;
        this.method = method;
        this.timestamp = new Date();
    }

}
