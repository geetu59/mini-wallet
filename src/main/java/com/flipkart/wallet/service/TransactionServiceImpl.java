package com.flipkart.wallet.service;

import com.flipkart.wallet.entity.Transaction;

import java.util.*;

public class TransactionServiceImpl implements TransactionService {
    private Map<String, List<Transaction>> transactionsMap;

    public TransactionServiceImpl() {
        this.transactionsMap = new HashMap<>();
    }

    @Override
    public void addTransaction(String name, double amount, TransactionType type, PaymentMethod method) {
        Transaction transaction = new Transaction(name, amount, type, method);
        transactionsMap.computeIfAbsent(name, k -> new ArrayList<>()).add(transaction);
    }


    @Override
    public List<Transaction> getTransactions(String name) {
        return transactionsMap.getOrDefault(name, Collections.emptyList());
    }
}
