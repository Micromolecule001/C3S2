package org.example.ui;

import javax.swing.*;
import java.awt.*;
import org.example.logic.Choice;
import org.example.logic.GameLogic;

public class GameWindow extends JFrame {
    private final boolean isVsComputer;
    private JLabel player1ChoiceLabel;
    private JLabel player2ChoiceLabel;
    private JLabel resultLabel;
    private Choice player1Choice;
    private Choice player2Choice;
    private boolean isPlayer1Turn;

    public GameWindow(boolean isVsComputer) {
        this.isVsComputer = isVsComputer;
        this.isPlayer1Turn = true;
        setTitle("Камень-Ножницы-Бумага");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Панель кнопок
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        for (Choice choice : Choice.values()) {
            JButton button = new JButton(choice.getDisplayName());
            button.addActionListener(e -> handleChoice(choice));
            buttonPanel.add(button);
        }

        // Панель результатов
        JPanel resultPanel = new JPanel(new GridLayout(1, 1));
        resultLabel = new JLabel("Сделайте ваш выбор!", SwingConstants.CENTER);
        resultPanel.add(resultLabel);

        // Панель ходов
        JPanel choicesPanel = new JPanel(new GridLayout(2, 2, 20, 10));
        player1ChoiceLabel = new JLabel("Игрок 1: -", SwingConstants.CENTER);
        player2ChoiceLabel = new JLabel(isVsComputer ? "Компьютер: -" : "Игрок 2: -", SwingConstants.CENTER);

        JButton buttonNewG = new JButton("New Game");
        JButton buttonMenu = new JButton("Menu");
        buttonNewG.addActionListener(e -> resetTurn());
        buttonMenu.addActionListener(e -> {
            new MenuWindow().setVisible(true); 
            dispose(); 
        });

        choicesPanel.add(buttonNewG);
        choicesPanel.add(buttonMenu);
        choicesPanel.add(player1ChoiceLabel);
        choicesPanel.add(player2ChoiceLabel);

        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel, BorderLayout.CENTER);
        mainPanel.add(choicesPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void handleChoice(Choice choice) {
        if (isPlayer1Turn) {
            player1Choice = choice;
            player1ChoiceLabel.setText("Игрок 1: " + choice.getDisplayName());
            if (isVsComputer) {
                player2Choice = GameLogic.getComputerChoice();
                player2ChoiceLabel.setText("Компьютер: " + player2Choice.getDisplayName());
                String winner = GameLogic.determineWinner(player1Choice, player2Choice);
                resultLabel.setText(winner);
                GameLogic.gameResults(player1Choice, player2Choice, winner);
            } else {
                isPlayer1Turn = false;
                resultLabel.setText("Игрок 2, ваш ход!");
            }
        } else {
            player2Choice = choice;
            player2ChoiceLabel.setText("Игрок 2: " + player2Choice.getDisplayName());
            String winner = GameLogic.determineWinner(player1Choice, player2Choice);
            resultLabel.setText(winner);
            GameLogic.gameResults(player1Choice, player2Choice, winner);
        }
    }

    private void resetTurn() {
        isPlayer1Turn = true;
        player1Choice = null;
        player2Choice = null;
        player1ChoiceLabel.setText("Игрок 1: -");
        player2ChoiceLabel.setText(isVsComputer ? "Компьютер: -" : "Игрок 2: -");
        resultLabel.setText("Сделайте ваш выбор!");
    }
}
