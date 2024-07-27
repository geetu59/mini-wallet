package com.flipkart.wallet.service;

interface WalletService {
    void topUpWallet(String name, PaymentMethod method, double amount) throws Exception;
    double fetchBalance(String name) throws Exception;
    void sendMoney(String sender, String receiver, double amount) throws Exception;
    void createDeposit(String name, double amount) throws Exception;
    void bookDeposit(String name) throws Exception;
}
