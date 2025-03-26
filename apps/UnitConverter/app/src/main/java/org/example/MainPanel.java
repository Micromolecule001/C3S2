package org.example;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
        // Set layout and add components
        setLayout(new FlowLayout());
        JButton CalculateButton = add(new JButton("Calculate"));

        CalculateButton.addActionListener(new ActionListener() {
            @Override
            public void actioctionPerformer(ActionEvent e) {
                // performCalculations();
            }
        })
               
        
        // You can add more UI components here
    }
}

