package com.flipkart.wallet.service;

interface Rule {
    boolean isValid(String userId);
    String getErrorMessage();
}
