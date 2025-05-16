package org.example.logic;


import java.util.Random;

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
}
