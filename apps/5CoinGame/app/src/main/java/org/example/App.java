package org.example;

import org.example.ui.GameUI;

import com.formdev.flatlaf.FlatLightLaf;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        try {
            // Set FlatLaf theme
            FlatLightLaf.setup();

            FlatLightLaf.setGlobalExtraDefaults(java.util.Map.of(
                "@font", "Ubuntu Mono-BOLD-18"
            ));
        } catch (Exception e) {
            System.err.println("Failed to initialize LaF or font");
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            javax.swing.JFrame frame = new javax.swing.JFrame("FlatLaf Demo"); 
            new GameUI();
        });
    }
}

