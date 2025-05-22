package org.example.ui;

import org.example.logic.ATM;
import org.example.logic.Bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class ATMFrame extends JFrame {
    private final ATM atm;
    private final JTextArea output;
    private String demoAccountId;

    public ATMFrame() {
        super("Симуляція роботи банкомату");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        Bank bank = new Bank();
        this.atm = new ATM(bank);

        JPanel panel = new JPanel(new GridLayout(6, 1)); // 6 кнопок

        JButton openAccount = new JButton("Відкрити рахунок");
        JButton closeAccount = new JButton("Закрити рахунок");
        JButton deposit = new JButton("Поповнити рахунок");
        JButton withdraw = new JButton("Зняти гроші");
        JButton demoButton = new JButton("Демо багатопоточності"); // нова кнопка

        output = new JTextArea();
        output.setEditable(false);

        openAccount.addActionListener(this::handleOpen);
        closeAccount.addActionListener(this::handleClose);
        deposit.addActionListener(this::handleDeposit);
        withdraw.addActionListener(this::handleWithdraw);
        demoButton.addActionListener(this::handleDemo); // обробка

        panel.add(openAccount);
        panel.add(closeAccount);
        panel.add(deposit);
        panel.add(withdraw);
        panel.add(demoButton); // додано кнопку
        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);
    }

    private void handleOpen(ActionEvent e) {
        String name = JOptionPane.showInputDialog(this, "Ім’я користувача:");
        if (name != null) {
            String accountId = atm.openAccount(name);
            output.append("Відкрито рахунок з ID: " + accountId + "\n");
        }
    }

    private void handleClose(ActionEvent e) {
        String id = JOptionPane.showInputDialog(this, "ID рахунку для закриття:");
        if (id != null) {
            boolean result = atm.closeAccount(id);
            output.append(result ? "Рахунок закрито.\n" : "Не вдалося закрити рахунок.\n");
        }
    }

    private void handleDeposit(ActionEvent e) {
        String id = JOptionPane.showInputDialog(this, "ID рахунку:");
        String amountStr = JOptionPane.showInputDialog(this, "Сума поповнення:");
        try {
            double amount = Double.parseDouble(amountStr);
            boolean result = atm.deposit(id, amount);
            output.append(result ? "Рахунок поповнено.\n" : "Помилка поповнення.\n");
        } catch (NumberFormatException ex) {
            output.append("Невірний формат суми.\n");
        }
    }

    private void handleWithdraw(ActionEvent e) {
        String id = JOptionPane.showInputDialog(this, "ID рахунку:");
        String amountStr = JOptionPane.showInputDialog(this, "Сума зняття:");
        try {
            double amount = Double.parseDouble(amountStr);
            boolean result = atm.withdraw(id, amount);
            output.append(result ? "Гроші знято.\n" : "Не вдалося зняти гроші.\n");
        } catch (NumberFormatException ex) {
            output.append("Невірний формат суми.\n");
        }
    }

    private void handleDemo(ActionEvent e) {
        if (demoAccountId == null) {
            demoAccountId = atm.openAccount("Demo User");
            output.append("⚙️ Створено демо-рахунок з ID: " + demoAccountId + "\n");
        }

        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                for (int j = 0; j < 5; j++) {
                    double amount = ThreadLocalRandom.current().nextDouble(10, 100);
                    boolean isDeposit = ThreadLocalRandom.current().nextBoolean();

                    boolean result;
                    String action;
                    if (isDeposit) {
                        result = atm.deposit(demoAccountId, amount);
                        action = "поповнення на " + String.format("%.2f", amount);
                    } else {
                        result = atm.withdraw(demoAccountId, amount);
                        action = "зняття на " + String.format("%.2f", amount);
                    }

                    String log = "[" + threadName + "] " + action + (result ? " ✔️" : " ❌") + "\n";
                    SwingUtilities.invokeLater(() -> output.append(log));

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }
        executor.shutdown();
    }
}
