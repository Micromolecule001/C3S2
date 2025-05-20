package org.example.ui;

import org.example.logic.GameLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameUI {
    private final GameLogic gameLogic = new GameLogic();
    private char currentPlayer = GameLogic.PLAYER_X;

    public void start() {
        JFrame frame = new JFrame("Гра: П'ять в ряд");
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
                            g.drawString(String.valueOf(symbol), j * cellSize + cellSize / 2, i * cellSize + cellSize / 2);
                        }
                    }
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int cellSize = panel.getWidth() / GameLogic.SIZE;
                int row = e.getY() / cellSize;
                int col = e.getX() / cellSize;
                if (gameLogic.makeMove(row, col, currentPlayer)) {
                    if (gameLogic.checkWin(currentPlayer)) {
                        JOptionPane.showMessageDialog(frame, "Гравець " + currentPlayer + " виграв!");
                    }
                    currentPlayer = (currentPlayer == GameLogic.PLAYER_X) ? GameLogic.PLAYER_O : GameLogic.PLAYER_X;
                    panel.repaint();
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}

