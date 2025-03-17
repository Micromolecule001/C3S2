package com.textanalyzer.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Text Analyzer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        // Create UI Panel
        TextAnalyzerUI uiPanel = new TextAnalyzerUI();
        add(uiPanel);

        setVisible(true);
    }
}

