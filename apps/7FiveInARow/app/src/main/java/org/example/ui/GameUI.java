package org.example.ui;

import org.example.logic.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameUI {
    private final GameLogic gameLogic = new GameLogic();
    private char currentPlayer = GameLogic.PLAYER_X;
    private JFrame frame;

    public void start() {
        frame = new JFrame("Гра: П’ять в ряд");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        JPanel panel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int cellSize = getWidth() / GameLogic.SIZE;
                for (int i = 0; i < GameLogic.SIZE; i++) {
                    for (int j = 0; j < GameLogic.SIZE; j++) {
                        g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                        char symbol = gameLogic.getBoard()[i][j];
                        if (symbol != GameLogic.EMPTY) {
                            g.setFont(new Font("Arial", Font.PLAIN, 20));
                            g.drawString(String.valueOf(symbol), j * cellSize + cellSize / 3, i * cellSize + 2 * cellSize / 3);
                        }
                    }
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (currentPlayer == GameLogic.PLAYER_X) { // Хід гравця
                    int cellSize = panel.getWidth() / GameLogic.SIZE;
                    int row = e.getY() / cellSize;
                    int col = e.getX() / cellSize;
                    if (gameLogic.makeMove(row, col, currentPlayer)) {
                        panel.repaint();
                        if (gameLogic.checkWin(currentPlayer)) {
                            JOptionPane.showMessageDialog(frame, "Гравець " + currentPlayer + " виграв!");
                            frame.dispose();
                            return;
                        }
                        currentPlayer = GameLogic.PLAYER_O;
                        handleComputerMove(panel); // Викликаємо хід комп’ютера
                    }
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void handleComputerMove(JPanel panel) {
        int[] move = gameLogic.computerMove();
        panel.repaint();
        if (gameLogic.checkWin(GameLogic.PLAYER_O)) {
            JOptionPane.showMessageDialog(frame, "Комп’ютер виграв!");
            frame.dispose();
        } else {
            currentPlayer = GameLogic.PLAYER_X;
        }
    }
}
