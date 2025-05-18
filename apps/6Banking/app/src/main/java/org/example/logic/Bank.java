package org.example.logic;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Bank {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();

    public String createAccount(String owner) {
        String id = UUID.randomUUID().toString();
        accounts.put(id, new Account(id, owner));
        return id;
    }

    public boolean deleteAccount(String id) {
        return accounts.remove(id) != null;
    }

    public boolean deposit(String id, double amount) {
        Account acc = accounts.get(id);
        if (acc == null) return false;
        acc.deposit(amount);
        return true;
    }

    public boolean withdraw(String id, double amount) {
        Account acc = accounts.get(id);
        if (acc == null || acc.getBalance() < amount) return false;
        acc.withdraw(amount);
        return true;
    }

    public double getBalance(String id) {
        Account acc = accounts.get(id);
        return acc != null ? acc.getBalance() : -1;
    }
}

