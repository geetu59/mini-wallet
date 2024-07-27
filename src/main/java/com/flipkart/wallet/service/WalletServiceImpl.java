package com.flipkart.wallet.service;

import com.flipkart.wallet.entity.User;
import com.flipkart.wallet.entity.Wallet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WalletServiceImpl implements WalletService {
    private UserService userService;
    private TransactionService transactionService;
    private Map<String, Rule> rules;

    public WalletServiceImpl(UserService userService, TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.rules = new HashMap<>();
    }

    public void addRule(String paymentMethod, Rule rule) {
        rules.put(paymentMethod, rule);
    }

    @Override
    public void topUpWallet(String name, PaymentMethod method, double amount) throws Exception {
        Rule rule = rules.get(method);
        if (rule != null && !rule.isValid(name)) {
            throw new IllegalArgumentException(rule.getErrorMessage());
        }

        User user = userService.getUser(name);
        Wallet wallet = user.getWallet();

        if (rule instanceof DebitCardRule) {
            amount = ((DebitCardRule) rule).applyPenalty(name, amount);
        }

        if (amount > 0) {
            wallet.addBalance(amount);
            transactionService.addTransaction(name, amount, TransactionType.valueOf("Deposit"), method);
        } else throw new Exception("Amount should be greater than 0");

        if (rule != null) {
            if (rule instanceof CreditCardRule) {
                ((CreditCardRule) rule).addTransaction(name);
            } else if (rule instanceof DebitCardRule) {
                ((DebitCardRule) rule).addTransaction(name);
            }
        }

    }

    @Override
    public double fetchBalance(String name) throws Exception {
        User user = userService.getUser(name);
        return user.getWallet().getBalance();
    }

    @Override
    public void sendMoney(String sender, String receiver, double amount) throws Exception {
        if (amount > 0) {
            User fromUser = userService.getUser(sender);
            User toUser = userService.getUser(receiver);
            Wallet fromWallet = fromUser.getWallet();
            Wallet toWallet = toUser.getWallet();

            if (fromWallet.getBalance() >= amount) {
                fromWallet.deductBalance(amount);
                toWallet.addBalance(amount);
                transactionService.addTransaction(sender, -amount, TransactionType.SEND, PaymentMethod.WALLET);
                transactionService.addTransaction(receiver, amount, TransactionType.RECEIVE, PaymentMethod.WALLET);
            } else {
                throw new IllegalArgumentException("Insufficient balance.");
            }
        } else throw new Exception("Amount cannot be less than 0");
    }

    @Override
    public void createDeposit(String name, double amount) throws Exception {
        User user = userService.getUser(name);
        Wallet wallet = user.getWallet();

        if (wallet.getBalance() >= amount) {
            wallet.deductBalance(amount);
            wallet.addFixedDeposit(amount);
            transactionService.addTransaction(name, -amount, TransactionType.FD_CREATE, PaymentMethod.WALLET);
        } else {
            throw new IllegalArgumentException("Insufficient balance.");
        }
    }

    @Override
    public void bookDeposit(String name) throws Exception {
        User user = userService.getUser(name);
        Wallet wallet = user.getWallet();
        List<Double> userFDs = wallet.getFixedDeposits();

        if (!userFDs.isEmpty()) {
            double amount = userFDs.remove(0);
            wallet.addBalance(amount);
            transactionService.addTransaction(name, amount, TransactionType.FD_DISSOLVE, PaymentMethod.WALLET);
        } else {
            throw new IllegalArgumentException("No fixed deposit available.");
        }
    }
}
