package com.textanalyzer.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.File;

public class TextAnalyzerUI extends JPanel {
    private JTextArea textArea;
    private JButton loadButton;

    public TextAnalyzerUI() {
        setLayout(new BorderLayout());

        // Create text area
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Create button
        loadButton = new JButton("Choose file to Analize");
        loadButton.setPreferredSize(new Dimension(5,100));
        loadButton.setFont(new Font("Terminus", Font.BOLD, 24));
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChooseFile();
            }
        });

        // Add components
        add(scrollPane, BorderLayout.CENTER);
        add(loadButton, BorderLayout.SOUTH);
    }

    private void ChooseFile() {
        System.out.println("Entry \n");
        JFileChooser file_upload = new JFileChooser();
        file_upload.showOpenDialog(null);
        System.out.println("Create file \n");

        int res = file_upload.showOpenDialog(null);
        System.out.println(res);

        if (res == JFileChooser.APPROVE_OPTION) {
            System.out.println("Entry #2 file_path: \n");
            File file_path = new File(file_upload.getSelectedFile().getAbsolutePath());
            System.out.println(file_path);
        }
    }
}

