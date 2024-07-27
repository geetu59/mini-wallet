package com.flipkart.wallet.entity;

import java.util.Objects;

public class User {
    private String name;
    private Wallet wallet;

    public User(String name) {
        this.name = name;
        this.wallet = new Wallet(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(wallet, user.wallet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, wallet);
    }

    public Wallet getWallet() {
        return wallet;
    }
}
