package org.example.ui;

import javax.swing.*;
import java.awt.*;

public class MenuWindow extends JFrame {
    public MenuWindow() {
        setTitle("Rock-Paper-Scissors");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        JLabel titleLabel = new JLabel("Выберите режим игры", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Ubuntu", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(20));

        JButton playerVsPlayerButton = new JButton("Игрок против Игрока");
        playerVsPlayerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerVsPlayerButton.addActionListener(e -> {
            new GameWindow(false).setVisible(true);
            dispose();
        });
        panel.add(playerVsPlayerButton);

        panel.add(Box.createVerticalStrut(10));

        JButton playerVsComputerButton = new JButton("Игрок против Компьютера");
        playerVsComputerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerVsComputerButton.addActionListener(e -> {
            new GameWindow(true).setVisible(true);
            dispose();
        });
        panel.add(playerVsComputerButton);

        panel.add(Box.createVerticalStrut(10));

        JButton statisticsButton = new JButton("Статистика");
        statisticsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        statisticsButton.addActionListener(e -> {
            new result.StatisticsWindow(this).setVisible(true);
            setVisible(false);
        });
        panel.add(statisticsButton);

        panel.add(Box.createVerticalGlue());

        add(panel);
    }
}

