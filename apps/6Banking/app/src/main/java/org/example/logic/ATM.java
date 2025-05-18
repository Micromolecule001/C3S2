package org.example.logic;

import java.util.logging.Logger;

public class ATM {
    private static final Logger logger = Logger.getLogger(ATM.class.getName());
    private static final double MAX_WITHDRAW = 1000.0;
    private final Bank bank;

    public ATM(Bank bank) {
        this.bank = bank;
    }

    public String openAccount(String name) {
        String id = bank.createAccount(name);
        logger.info("Account opened: " + id);
        return id;
    }

    public boolean closeAccount(String id) {
        boolean result = bank.deleteAccount(id);
        logger.info("Account closed: " + id);
        return result;
    }

    public boolean deposit(String id, double amount) {
        boolean result = bank.deposit(id, amount);
        logger.info("Deposit to " + id + ": " + amount);
        return result;
    }

    public boolean withdraw(String id, double amount) {
        if (amount > MAX_WITHDRAW) {
            logger.warning("Withdraw amount exceeds limit: " + amount);
            return false;
        }
        boolean result = bank.withdraw(id, amount);
        logger.info("Withdraw from " + id + ": " + amount);
        return result;
    }
}

