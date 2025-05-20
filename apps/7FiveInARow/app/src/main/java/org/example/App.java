package org.example;

import org.example.ui.GameUI;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }
    
    public static void main(String[] args) {
        // Запускаємо гру через UI
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameUI gameUI = new GameUI();
            gameUI.start();
        });
    }
}
