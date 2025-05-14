package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import org.example.ui.GameWindow;

import javax.swing.*;

public class App {
    
    public static String getGreeting() {
        return "Hello";
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
           e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new App().initUI());
}

