package com.flipkart.wallet.service;

import com.flipkart.wallet.entity.User;

import java.util.HashSet;

public class UserService {
    HashSet<User> users = new HashSet<>();

    public void registerUser(String name) throws Exception {
        User newUser = new User(name);
        if (users.contains(newUser)) {
            throw new Exception("User already exists");
        } else users.add(newUser);
    }

    public User getUser(String name) throws Exception {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        throw new Exception("User not found");
    }
}
