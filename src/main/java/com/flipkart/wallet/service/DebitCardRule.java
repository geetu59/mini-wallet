package com.flipkart.wallet.service;

import java.util.HashMap;
import java.util.Map;

public class DebitCardRule implements Rule{
    private int penaltyAfterTransactions;
    private double penaltyAmount;
    private Map<String, Integer> userTransactions;

    public DebitCardRule(int penaltyAfterTransactions, double penaltyAmount) {
        this.penaltyAfterTransactions = penaltyAfterTransactions;
        this.penaltyAmount = penaltyAmount;
        this.userTransactions = new HashMap<>();
    }

    @Override
    public boolean isValid(String userId) {
        int transactions = userTransactions.getOrDefault(userId, 0);
        return true;
    }

    @Override
    public String getErrorMessage() {
        return "Debit Card: Penalty applied after exceeding transactions.";
    }

    public double applyPenalty(String userId, double amount) {
        int transactions = userTransactions.getOrDefault(userId, 0);
        if (transactions >= penaltyAfterTransactions) {
            return amount - penaltyAmount;
        }
        return amount;
    }
    public void addTransaction(String userId) {
        int transactions = userTransactions.getOrDefault(userId, 0);
        userTransactions.put(userId, transactions + 1);
    }
}
