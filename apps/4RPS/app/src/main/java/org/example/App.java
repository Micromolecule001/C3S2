package org.example;

import java.util.HashMap;
import javax.swing.*;
import java.awt.*;

import com.formdev.flatlaf.FlatLightLaf;
        
public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    private JFrame mainFrame;
    private JButton rockButton, paperButton, scissorsButton;
    private JLabel resultLabel, playerLabel, computerLabel;
    private JLabel resultImageLabel;

    public App() {
        initUI();
    }

    public static void main(String[] args) {
        try {
                UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
        }

        SwingUtilities.invokeLater(App::new);
    }

    private void initUI() {
        mainFrame = new JFrame("Rock, Paper, Scissors");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        mainFrame.setLayout(new BorderLayout());

        // North: Result
        resultLabel = new JLabel("Choose your move!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Ubuntu Nerd Font", Font.BOLD, 22));
        mainFrame.add(resultLabel, BorderLayout.NORTH);


        // Center: result image meme
        resultImageLabel = new JLabel();
        resultImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultImageLabel.setVisible(false); // Hide initially
        mainFrame.add(resultImageLabel, BorderLayout.EAST); // or CENTER if replacing buttons


        // Center: Icons or buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());

        rockButton = new JButton("🪨 Rock");
        paperButton = new JButton("📄 Paper");
        scissorsButton = new JButton("✂️ Scissors");

        buttonPanel.add(rockButton);
        buttonPanel.add(paperButton);
        buttonPanel.add(scissorsButton);
        
        

        mainFrame.add(buttonPanel, BorderLayout.CENTER);

        // South: Info
        JPanel statusPanel = new JPanel(new GridLayout(1, 2));
        playerLabel = new JLabel("You: ", SwingConstants.CENTER);
        computerLabel = new JLabel("Computer: ", SwingConstants.CENTER);
        statusPanel.add(playerLabel);
        statusPanel.add(computerLabel);

        mainFrame.add(statusPanel, BorderLayout.SOUTH);

        // Events
        rockButton.addActionListener(e -> play("ROCK"));
        paperButton.addActionListener(e -> play("PAPER"));
        scissorsButton.addActionListener(e -> play("SCISSORS"));

        mainFrame.setVisible(true);
    }

   private void play(String playerChoice) {
            String[] choices = {"ROCK", "PAPER", "SCISSORS"};
            String computerChoice = choices[(int)(Math.random() * choices.length)];

            HashMap<String, String> choicesImages = new HashMap<>();
            choicesImages.put("ROCK", "/rock.jpg");
            choicesImages.put("PAPER", "/paper.jpg");
            choicesImages.put("SCISSORS", "/scissors.jpg");

            String resultImagePath = choicesImages.get(playerChoice);
            java.net.URL imageUrl = getClass().getResource(resultImagePath);

            if (imageUrl != null) {
                resultImageLabel.setIcon(new ImageIcon(imageUrl));
                resultImageLabel.setVisible(true);
            } else {
                System.err.println("Image not found: " + resultImagePath);
            }

            playerLabel.setText("You: " + playerChoice);
            computerLabel.setText("Computer: " + computerChoice);

            String result;
            if (playerChoice.equals(computerChoice)) {
                result = "Draw!";
            } else if (
                (playerChoice.equals("ROCK") && computerChoice.equals("SCISSORS")) ||
                (playerChoice.equals("PAPER") && computerChoice.equals("ROCK")) ||
                (playerChoice.equals("SCISSORS") && computerChoice.equals("PAPER"))
            ) {
                result = "You Win!";
            } else {
                result = "You Lose!";
            }

            resultLabel.setText(result);
        }
}

