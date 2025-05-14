package org.example.logic;

public class GameLogic {
    public static String getResult(Choice player, Choice computer) {
        if (player == computer) return "Draw!";
        if (player.beats(computer)) return "You Win!";
        return "You Lose!";
    }

    public static String getImagePath(Choice choice) {
        return switch (choice) {
            case ROCK -> "/rock.jpg";
            case PAPER -> "/paper.jpg";
            case SCISSORS -> "/scissors.jpg";
        };
    }
}

