package org.example.logic;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameLogic {
    private static final Random random = new Random();

    public static Choice getComputerChoice() {
        System.out.println("Into getComputerChoice");
        Choice[] choices = Choice.values();
        return choices[random.nextInt(choices.length)];
    }

    public static String determineWinner(Choice player1, Choice player2) {
        System.out.println("Into determneWinner: " + player1 + " " + player2);
        if (player1 == player2) {
            return "Ничья!";
        }
        switch (player1) {
            case ROCK:
                return (player2 == Choice.SCISSORS) ? "Игрок 1 победил!" : "Игрок 2 победил!";
            case PAPER:
                return (player2 == Choice.ROCK) ? "Игрок 1 победил!" : "Игрок 2 победил!";
            case SCISSORS:
                return (player2 == Choice.PAPER) ? "Игрок 1 победил!" : "Игрок 2 победил!";
            default:
                return "Ошибка!";
        }
    }

    public static void gameResults(Choice player1, Choice player2, String winner) {
        // Generate timestamp
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HHmmss").format(new Date());

        // Create result directory (../results/)
        File resultDir = new File("../results");
        if (!resultDir.exists()) {
            resultDir.mkdirs();
        }

        // Create file
        File outputFile = new File(resultDir, timestamp + ".txt"); // Changed extension to .txt

        // Write results in a simple text format
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write("Game Result\n");
            writer.write("Timestamp: " + timestamp + "\n");
            writer.write("Player 1: " + player1.toString() + "\n");
            writer.write("Player 2: " + player2.toString() + "\n");
            writer.write("Winner: " + winner + "\n");
            System.out.println("Saved game result to " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save result: " + e.getMessage());
        }
    }
}
