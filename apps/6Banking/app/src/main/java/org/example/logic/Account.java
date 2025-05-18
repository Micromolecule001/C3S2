package org.example.logic;

public class Account {
    private final String id;
    private final String owner;
    private double balance;

    public Account(String id, String owner) {
        this.id = id;
        this.owner = owner;
        this.balance = 0.0;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }
}

