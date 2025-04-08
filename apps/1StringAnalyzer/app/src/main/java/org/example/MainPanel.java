package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private JTextArea textArea;
    private JButton btn;

    public MainPanel() {
        // Use BorderLayout for better arrangement.
        setLayout(new BorderLayout());

        // Initialize textArea and wrap it in a scroll pane.
        textArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Initialize the button with a label.
        btn = new JButton("Choose File & Analyze");

        // Add components to the panel.
        add(scrollPane, BorderLayout.CENTER);
        add(btn, BorderLayout.SOUTH);

        // Add ActionListener to the button.
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseAndAnalyzeFile();
            }
        });
    }

    /**
     * Opens a file chooser dialog, reads the selected file,
     * analyzes its content, and outputs statistics to the text area.
     */
    private void chooseAndAnalyzeFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                // Read file content as a UTF-8 string.
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
                // Analyze the text and get statistics.
                String analysis = analyzeText(content);
                // Display the analysis in the text area.
                textArea.setText(analysis);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Analyzes the given text and returns a summary of statistics.
     *
     * @param text the text to analyze
     * @return a string containing the analysis
     */
    private String analyzeText(String text) {
        // Normalize the text to lower-case.
        text = text.toLowerCase();

        // Split text into words.
        String[] words = text.split("\\W+");
        int totalWords = words.length;
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(words));
        int uniqueWordCount = uniqueWords.size();

        // Split text into sentences (assuming sentences end with ., !, or ?).
        String[] sentences = text.split("[.!?]+");
        int sentenceCount = sentences.length;

        // Count punctuation marks.
        Matcher matcher = Pattern.compile("\\p{Punct}").matcher(text);
        int punctuationCount = 0;
        while (matcher.find()) {
            punctuationCount++;
        }

        // Calculate average word length.
        int totalWordLength = Arrays.stream(words).mapToInt(String::length).sum();
        double avgWordLength = totalWords > 0 ? (double) totalWordLength / totalWords : 0;

        // Calculate average sentence length in terms of word count.
        double avgSentenceLength = sentenceCount > 0 ? (double) totalWords / sentenceCount : 0;

        // Determine top 10 frequent words.
        Map<String, Integer> frequency = new HashMap<>();
        for (String word : words) {
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }
        List<Map.Entry<String, Integer>> sortedFrequency = new ArrayList<>(frequency.entrySet());
        sortedFrequency.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        StringBuilder topWords = new StringBuilder();
        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedFrequency) {
            if (count++ >= 10) break;
            topWords.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }

        // Build and return the analysis summary.
        StringBuilder analysis = new StringBuilder();
        analysis.append("Total words: ").append(totalWords).append("\n");
        analysis.append("Unique words: ").append(uniqueWordCount).append("\n");
        analysis.append("Sentences: ").append(sentenceCount).append("\n");
        analysis.append("Punctuation marks: ").append(punctuationCount).append("\n");
        analysis.append("Average word length: ").append(String.format("%.2f", avgWordLength)).append("\n");
        analysis.append("Average sentence length (words): ").append(String.format("%.2f", avgSentenceLength)).append("\n");
        analysis.append("Top 10 frequent words:\n").append(topWords);

        return analysis.toString();
    }
}
