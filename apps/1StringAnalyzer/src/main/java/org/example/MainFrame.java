package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

public class MainFrame extends JFrame {
        public MainFrame() {
                try {
                        UIManager.setLookAndFeel(new FlatLightLaf());
                } catch (UnsupportedLookAndFeelException e) {
                        e.printStackTrace();
                }

                setTitle("String Stats Analyzer");
                setSize(300,200);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                
                add(new MainPanel());
        }

        public static void main(String[] args) {
                SwingUtilities.invokeLater(() -> {
                        MainFrame frame = new MainFrame();
                        frame.setVisible(true);
                });
        }

}
