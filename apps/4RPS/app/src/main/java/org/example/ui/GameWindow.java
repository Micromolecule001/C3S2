package org.example.ui;

import org.example.logic.Choice;
import org.example.logic.GameLogic;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private JFrame mainFrame;    
    private JPanel mainPanel;
    private CardLayout cardLayout;

    private JLabel resultLabel;
    private JLabel resultImageLabel;
    private JLabel playerLabel;
    private JLabel computerLabel;

    private JButton rockButton;
    private JButton paperButton;
    private JButton scissorsButton;

    public void initUI() {
        mainFrame = new JFrame("Rock, Paper, Scissors");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createMenuPanel(), "menu");
        mainPanel.add(createGamePanel(), "game");

        mainFrame.add(mainPanel);
        cardLayout.show(mainPanel, "menu");

        mainFrame.setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1));

        JLabel title = new JLabel("Select Mode", SwingConstants.CENTER);
        title.setFont(new Font("Ubuntu Nerd Font", Font.BOLD, 22));
        panel.add(title);

        JButton pvpButton = new JButton("👤 vs 👤 Player");
        JButton pvcButton = new JButton("👤 vs 🤖 Computer");

        pvpButton.addActionListener(e -> {
            startGame("PVP");
        });

        pvcButton.addActionListener(e -> {
            startGame("PVC");
        });

        panel.add(pvpButton);
        panel.add(pvcButton);

        return panel;
    }

    private JPanel createGamePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        resultLabel = new JLabel("Choose your move!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Ubuntu Nerd Font", Font.BOLD, 22));
        panel.add(resultLabel, BorderLayout.NORTH);

        resultImageLabel = new JLabel();
        resultImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultImageLabel.setVisible(false);
        panel.add(resultImageLabel, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        rockButton = new JButton("🪨 Rock");
        paperButton = new JButton("📄 Paper");
        scissorsButton = new JButton("✂️ Scissors");

        rockButton.addActionListener(e -> play("ROCK"));
        paperButton.addActionListener(e -> play("PAPER"));
        scissorsButton.addActionListener(e -> play("SCISSORS"));

        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));

        JPanel statusPanel = new JPanel(new GridLayout(1, 2));
        playerLabel = new JLabel("You: ", SwingConstants.CENTER);
        computerLabel = new JLabel("Opponent: ", SwingConstants.CENTER);
        statusPanel.add(playerLabel);
        statusPanel.add(computerLabel);

        JButton backButton = new JButton("🔙 Back to Menu");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "menu"));

        bottomPanel.add(statusPanel);
        bottomPanel.add(backButton);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void play(Choice playerChoice) {
        Choice computerChoice = Choice.random();
        playerLabel.setText("You: " + playerChoice);
        computerLabel.setText("Computer: " + computerChoice);

        String resultText = GameLogic.getResult(playerChoice, computerChoice);
        resultLabel.setText(resultText);

        String imagePath = GameLogic.getImagePath(playerChoice);
        var imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            resultImageLabel.setIcon(new ImageIcon(imageUrl));
            resultImageLabel.setVisible(true);
        } else {
            System.err.println("Image not found: " + imagePath);
        }
    }
}

