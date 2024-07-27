package com.flipkart.wallet.entity;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
private String userName;
    private double balance;
    private List<Double> fixedDeposits;

    public Wallet(String userId) {
        this.userName = userId;
        this.balance = 0.0;
        this.fixedDeposits = new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public void deductBalance(double amount) {
        this.balance -= amount;
    }

    public List<Double> getFixedDeposits() {
        return fixedDeposits;
    }

    public void addFixedDeposit(double amount) {
        this.fixedDeposits.add(amount);
    }

    public void removeFixedDeposit(double amount) {
        this.fixedDeposits.remove(amount);
    }
}
