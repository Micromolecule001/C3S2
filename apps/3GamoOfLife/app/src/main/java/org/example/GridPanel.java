package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GridPanel extends JPanel {
    private int[][] grid;
    private int cellSize = 20;
    private Timer timer;
    private boolean isRunning = true;
    private JButton pauseButton;
    private JFrame statsFrame;
    private JLabel statsLabel;

    public GridPanel(int[][] grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(grid[0].length * cellSize, grid.length * cellSize));
        initTimer();
        initPauseButton();
        initStatsWindow();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
        });
    }

    private void initTimer() {
        // Timer that ticks every 200 milliseconds
        timer = new Timer(200, e -> {
            if (isRunning) {
                updateGrid();  // Calculate next generation
                repaint();     // Redraw the grid
            }
        });
        timer.start();
    }

    private void initPauseButton() {
        pauseButton = new JButton("Pause");
        pauseButton.setBounds(10, 10, 80, 30);
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleTimer();
            }
        });
        this.setLayout(null);
        this.add(pauseButton);
    }

    private void initStatsWindow() {
        statsFrame = new JFrame("Living Cells Statistics");
        statsFrame.setSize(200, 100);
        statsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        statsFrame.setLocationRelativeTo(null);
        statsLabel = new JLabel("Living Cells: 0", SwingConstants.CENTER);
        statsFrame.add(statsLabel);
        statsFrame.setVisible(true);
    }

    private void toggleTimer() {
        isRunning = !isRunning;
        pauseButton.setText(isRunning ? "Pause" : "Resume");
    }

    private void updateGrid() {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] newGrid = new int[rows][cols];

        // For each cell, count live neighbors and update newGrid accordingly
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int liveNeighbors = countLiveNeighbors(row, col);
                // Apply Conway's Game of Life rules:
                if (grid[row][col] == 1) { // Cell is alive
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        newGrid[row][col] = 0;  // Cell dies
                    } else {
                        newGrid[row][col] = 1;  // Cell lives
                    }
                } else { // Cell is dead
                    if (liveNeighbors == 3) {
                        newGrid[row][col] = 1;  // Cell becomes alive
                    } else {
                        newGrid[row][col] = 0;  // Cell remains dead
                    }
                }
            }
        }
        grid = newGrid;  // Update grid reference
    }

    private int countLiveNeighbors(int row, int col) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Skip the cell itself
                int r = row + i;
                int c = col + j;
                if (r >= 0 && r < rows && c >= 0 && c < cols) {
                    count += grid[r][c];
                }
            }
        }
        return count;
    }

    private void handleMouseClick(MouseEvent e) {
        int col = e.getX() / cellSize;
        int row = e.getY() / cellSize;
        if (row >= 0 && row < grid.length && col >= 0 && col < grid[row].length) {
            // Toggle the cell's state
            grid[row][col] = (grid[row][col] == 1) ? 0 : 1;
            repaint(); // Redraw the grid
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw grid cells
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 1) {
                    g.setColor(Color.BLACK);
                    g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                }
                g.setColor(Color.GRAY);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }
}

