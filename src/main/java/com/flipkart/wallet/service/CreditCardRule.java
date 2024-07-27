package com.flipkart.wallet.service;

import java.util.HashMap;
import java.util.Map;

public class CreditCardRule implements Rule{
    private int maxTransactions;
    private Map<String, Integer> userTransactions;

    public CreditCardRule(int maxTransactions) {
        this.maxTransactions = maxTransactions;
        this.userTransactions = new HashMap<>();
    }

    @Override
    public boolean isValid(String userId) {
        int transactions = userTransactions.getOrDefault(userId, 0);
        return transactions < maxTransactions;
    }

    @Override
    public String getErrorMessage() {
        return "Credit Card: Maximum transactions exceeded.";
    }

    public void addTransaction(String userId) {
        int transactions = userTransactions.getOrDefault(userId, 0);
        userTransactions.put(userId, transactions + 1);
    }
}
