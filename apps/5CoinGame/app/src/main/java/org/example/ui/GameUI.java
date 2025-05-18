package org.example.ui;

import org.example.logic.GameLogic;

import javax.swing.*;
import java.awt.*;

public class GameUI extends JFrame {
    private final GameLogic logic = new GameLogic();
    private final JLabel coinLabel = new JLabel();
    private final JLabel turnLabel = new JLabel();
    private final JButton take1 = new JButton("Взяти 1");
    private final JButton take2 = new JButton("Взяти 2");

    public GameUI() {
        setTitle("Гра в монети");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        add(coinLabel);
        add(turnLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(take1);
        buttonPanel.add(take2);
        add(buttonPanel);

        JButton restart = new JButton("Почати заново");
        add(restart);

        take1.addActionListener(e -> playerTurn(1));
        take2.addActionListener(e -> playerTurn(2));
        restart.addActionListener(e -> {
            logic.reset();
            updateUI();
        });

        updateUI();
        setVisible(true);
    }

    private void updateUI() {
        coinLabel.setText("Монет залишилось: " + logic.getCoins());
        turnLabel.setText(logic.isUserTurn() ? "Ваш хід" : "Хід комп’ютера");

        take1.setEnabled(logic.isUserTurn() && logic.getCoins() >= 1);
        take2.setEnabled(logic.isUserTurn() && logic.getCoins() >= 2);

        if (!logic.isUserTurn() && !logic.isGameOver()) {
            SwingUtilities.invokeLater(() -> {
                int taken = logic.computerMove();
                JOptionPane.showMessageDialog(this, "Комп’ютер взяв " + taken + " монет");
                checkEnd();
                updateUI();
            });
        }
    }

    private void playerTurn(int n) {
        logic.userMove(n);
        checkEnd();
        updateUI();
    }

    private void checkEnd() {
        if (logic.isGameOver()) {
            JOptionPane.showMessageDialog(this, "Гру завершено! Переможець: " + logic.getWinner());
            take1.setEnabled(false);
            take2.setEnabled(false);
        }
    }
}

