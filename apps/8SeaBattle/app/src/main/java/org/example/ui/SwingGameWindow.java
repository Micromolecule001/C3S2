package org.example.ui;

import org.example.logic.Board;
import org.example.logic.Cell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SwingGameWindow extends JFrame {
    private final Board playerBoard;
    private final Board computerBoard;
    private final JPanel playerPanel = new JPanel(new GridLayout(10, 10));
    private final JPanel computerPanel = new JPanel(new GridLayout(10, 10));
    private final JLabel statusLabel = new JLabel("Ваш хід");

    private final JButton[][] computerButtons = new JButton[10][10];
    private final boolean[][] computerRevealed = new boolean[10][10];

    public SwingGameWindow(Board playerBoard, Board computerBoard) {
        this.playerBoard = playerBoard;
        this.computerBoard = computerBoard;

        setTitle("Морський бій");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout());

        JPanel boards = new JPanel(new GridLayout(1, 2));
        boards.add(createBoardPanel(playerPanel, playerBoard, false));
        boards.add(createBoardPanel(computerPanel, computerBoard, true));

        add(boards, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createBoardPanel(JPanel panel, Board board, boolean isEnemy) {
        panel.setBorder(BorderFactory.createTitledBorder(isEnemy ? "Поле ворога" : "Ваше поле"));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(40, 40));
                btn.setBackground(Color.CYAN);

                if (!isEnemy) {
                    if (board.isShip(i, j)) btn.setBackground(Color.GRAY);
                    btn.setEnabled(false);
                } else {
                    int x = i, y = j;
                    computerButtons[i][j] = btn;
                    btn.addActionListener(e -> handlePlayerMove(x, y));
                }

                panel.add(btn);
            }
        }
        return panel;
    }

    private void handlePlayerMove(int x, int y) {
        if (computerRevealed[x][y]) return;

        boolean hit = computerBoard.shootAt(x, y);
        updateComputerBoard();
        computerRevealed[x][y] = true;
        JButton btn = computerButtons[x][y];
        btn.setBackground(hit ? Color.RED : Color.WHITE);
        btn.setEnabled(false);

        if (computerBoard.allShipsSunk()) {
            statusLabel.setText("Ви перемогли!");
            disableEnemyBoard();
            return;
        }

        statusLabel.setText(hit ? "Влучив! Ваш ще один хід." : "Мимо. Хід комп'ютера.");
        if (!hit) {
            computerMove();
        }
    }

    private void computerMove() {
        while (true) {
            int x = (int)(Math.random() * 10);
            int y = (int)(Math.random() * 10);
            Cell[][] grid = playerBoard.getGrid();
            if (!grid[x][y].isHit()) {
                boolean hit = playerBoard.shootAt(x, y);
                updatePlayerBoard();

                if (playerBoard.allShipsSunk()) {
                    statusLabel.setText("Комп'ютер переміг.");
                    disableEnemyBoard();
                    return;
                }

                if (!hit) {
                    statusLabel.setText("Ваш хід");
                    break;
                }
            }
        }
    }

    private void updatePlayerBoard() {
        Component[] components = playerPanel.getComponents();
        Cell[][] grid = playerBoard.getGrid();
        for (int i = 0; i < 10 * 10; i++) {
            int x = i / 10;
            int y = i % 10;
            JButton btn = (JButton) components[i];
            if (grid[x][y].isHit()) {
                btn.setBackground(grid[x][y].hasShip() ? Color.RED : Color.WHITE);
            }
        }
    }

    private void updateComputerBoard() {
       Cell[][] grid = computerBoard.getGrid();
       for (int x = 0; x < 10; x++) {
           for (int y = 0; y < 10; y++) {
               if (computerRevealed[x][y]) continue;
               if (grid[x][y].isHit()) {
                   computerButtons[x][y].setBackground(grid[x][y].hasShip() ? Color.RED : Color.WHITE);
                   computerButtons[x][y].setEnabled(false);
                   computerRevealed[x][y] = true;
               }
           }
       }
    }   

    private void disableEnemyBoard() {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                computerButtons[i][j].setEnabled(false);
    }
}
