package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class MainPanel extends JPanel {
    private JComboBox<String> optionsComboBox;
    private JPanel formPanel;
    private JTextField inputField;
    private JTextField outputField;
    private JComboBox<String> unitComboBox1;
    private JComboBox<String> unitComboBox2;
    private JButton calculateButton;

    public MainPanel() {
        setLayout(new BorderLayout());

        // Create top panel
        JPanel topPanel = new JPanel(new GridLayout());
        topPanel.setBorder(new EmptyBorder(20, 15, 10, 15));
        String[] options = { "time", "distance", "speed", "mass", "area", "volume", "pressure", "temperature", "energy" };
        optionsComboBox = new JComboBox<>(options);
        topPanel.add(new JLabel("Conversion Type:"));
        topPanel.add(optionsComboBox);
        add(topPanel, BorderLayout.NORTH);

        // Create form panel
        formPanel = new JPanel(new GridBagLayout());
        add(formPanel, BorderLayout.CENTER);

        // Create bottom panel with calculate button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        calculateButton = new JButton("Calculate");
        bottomPanel.add(calculateButton);
        bottomPanel.setPreferredSize(new Dimension(getWidth(), 50));
        add(bottomPanel, BorderLayout.SOUTH);

        // Update form when the selected option changes
        optionsComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String selectedOption = e.getItem().toString();
                    updateForm(selectedOption);
                }
            }
        });

        // Initialize form with the first option
        updateForm(optionsComboBox.getSelectedItem().toString());

        // Add ActionListener to the Calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    float inputValue = Float.parseFloat(inputField.getText());
                    String category = optionsComboBox.getSelectedItem().toString();
                    String fromUnit = unitComboBox1.getSelectedItem().toString();
                    String toUnit = unitComboBox2.getSelectedItem().toString();

                    float result = CalculateUnits.convert(category, fromUnit, toUnit, inputValue);
                    outputField.setText(String.valueOf(result));
                } catch (NumberFormatException ex) {
                    outputField.setText("Invalid Input");
                }
            }
        });
    }

    private void updateForm(String selectedOption) {
        formPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();

        // Input Label & Field
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1; gbc.ipadx = 5;
        formPanel.add(new JLabel("Input:"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 3;
        inputField = new JTextField(10);
        formPanel.add(inputField, gbc);

        // Output Label & Field (Read-only)
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        formPanel.add(new JLabel("Output:"), gbc);

        gbc.gridx = 1; gbc.gridwidth = 3;
        outputField = new JTextField(10);
        outputField.setEditable(false);
        formPanel.add(outputField, gbc);

        // Choose units based on selected category
        if (selectedOption.equals("time")) {
            unitComboBox1 = new JComboBox<>(new String[]{"seconds", "minutes", "hours"});
            unitComboBox2 = new JComboBox<>(new String[]{"seconds", "minutes", "hours"});
        } else if (selectedOption.equals("distance")) {
            unitComboBox1 = new JComboBox<>(new String[]{"meters", "kilometers", "miles"});
            unitComboBox2 = new JComboBox<>(new String[]{"meters", "kilometers", "miles"});
        } else if (selectedOption.equals("speed")) {
            unitComboBox1 = new JComboBox<>(new String[]{"m/s", "km/h", "mph"});
            unitComboBox2 = new JComboBox<>(new String[]{"m/s", "km/h", "mph"});
        } else if (selectedOption.equals("mass")) {
            unitComboBox1 = new JComboBox<>(new String[]{"grams", "kilograms", "pounds"});
            unitComboBox2 = new JComboBox<>(new String[]{"grams", "kilograms", "pounds"});
        } else if (selectedOption.equals("area")) {
            unitComboBox1 = new JComboBox<>(new String[]{"sq.meters", "sq.kilometers", "sq.feet"});
            unitComboBox2 = new JComboBox<>(new String[]{"sq.meters", "sq.kilometers", "sq.feet"});
        } else if (selectedOption.equals("volume")) {
            unitComboBox1 = new JComboBox<>(new String[]{"liters", "milliliters", "gallons"});
            unitComboBox2 = new JComboBox<>(new String[]{"liters", "milliliters", "gallons"});
        } else if (selectedOption.equals("pressure")) {
            unitComboBox1 = new JComboBox<>(new String[]{"Pascals", "bars", "psi"});
            unitComboBox2 = new JComboBox<>(new String[]{"Pascals", "bars", "psi"});
        } else if (selectedOption.equals("temperature")) {
            unitComboBox1 = new JComboBox<>(new String[]{"Celsius", "Fahrenheit", "Kelvin"});
            unitComboBox2 = new JComboBox<>(new String[]{"Celsius", "Fahrenheit", "Kelvin"});
        } else if (selectedOption.equals("energy")) {
            unitComboBox1 = new JComboBox<>(new String[]{"Joules", "Calories", "Watt-hours"});
            unitComboBox2 = new JComboBox<>(new String[]{"Joules", "Calories", "Watt-hours"});
        } else {
            unitComboBox1 = new JComboBox<>(new String[]{});
            unitComboBox2 = new JComboBox<>(new String[]{});
        }

        // Add unit selectors to form
        gbc.gridx = 4; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(unitComboBox1, gbc);

        gbc.gridx = 4; gbc.gridy = 1; gbc.gridwidth = 2;
        formPanel.add(unitComboBox2, gbc);

        // Refresh panel
        formPanel.revalidate();
        formPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Unit Converter");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new MainPanel());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

