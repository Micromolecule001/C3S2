ackage org.example.ui;

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

        // Add vertical glue to center the content
        panel.add(Box.createVerticalGlue());

        JLabel titleLabel = new JLabel("Выберите режим игры", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Ubuntu", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createVerticalStrut(20)); // spacing

        JButton playerVsPlayerButton = new JButton("Игрок против Игрока");
        playerVsPlayerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerVsPlayerButton.addActionListener(e -> {
            new GameWindow(false).setVisible(true);
            dispose();
        });
        panel.add(playerVsPlayerButton);

        panel.add(Box.createVerticalStrut(10)); // spacing

        JButton playerVsComputerButton = new JButton("Игрок против Компьютера");
        playerVsComputerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playerVsComputerButton.addActionListener(e -> {
            new GameWindow(true).setVisible(true);
            dispose();
        });
        panel.add(playerVsComputerButton);

        panel.add(Box.createVerticalGlue()); // push everything toward center

        add(panel);
    }
}

