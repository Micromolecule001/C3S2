package org.example;

import javax.swing.*;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game of Life");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            int[][] grid = new int[50][50];

            // Optionally, initialize some cells to be alive
            grid[24][24] = 1;
            grid[24][25] = 1;
            grid[24][26] = 1;
            grid[25][25] = 1;

            GridPanel gridPanel = new GridPanel(grid);
            frame.add(gridPanel);
            frame.pack();
            frame.setVisible(true);
        });
    }
}

