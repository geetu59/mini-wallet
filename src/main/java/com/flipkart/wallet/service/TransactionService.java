package com.flipkart.wallet.service;

import com.flipkart.wallet.entity.Transaction;

import java.util.List;

public interface TransactionService {
    void addTransaction(String name, double amount, TransactionType type, PaymentMethod method);
    List<Transaction> getTransactions(String name);

}
