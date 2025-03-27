package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.example.CalculateUnits;

public class MainPanel extends JPanel {
    public MainPanel() {
        // Set layout and add components

        String[] options = { "time", "distance", "speed", "mass", "area", "volume", "pressure", "temperature", "energy" };
        JComboBox optionsComboBox = new JComboBox(options);
        add(optionsComboBox);

        setLayout(new FlowLayout());
        JButton calculateButton = new JButton("Calculate");
        add(calculateButton);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CalculateUnits calculator = new CalculateUnits();
                float inputValue = 0.2f; // Example input value
                float result = calculator.calculateUnits(inputValue);

                // Display or use the result as needed
                System.out.println("Calculation result: " + result);
            }
        });
    }
}

