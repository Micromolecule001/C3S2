package org.example.ui;

import org.example.logic.Board;

import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);

    public void printMessage(String message) {
        System.out.println(message);
    }

    public int[] askForCoordinates() {
        System.out.print("Введіть координати (наприклад, A5): ");
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.length() >= 2) {
                char colChar = input.charAt(0);
                int row = -1;
                try {
                    row = Integer.parseInt(input.substring(1)) - 1;
                } catch (NumberFormatException e) {
                    printMessage("Невірний формат. Спробуйте ще раз.");
                    continue;
                }
                int col = colChar - 'A';
                if (col >= 0 && col < Board.SIZE && row >= 0 && row < Board.SIZE) {
                    return new int[]{row, col};
                }
            }
            printMessage("Невірний формат. Спробуйте ще раз.");
        }
    }

    public void printShotResult(boolean hit, Board board, int x, int y) {
        String result = hit ? "Влучання!" : "Промах.";
        System.out.println(result);
    }

    public void printBoards(Board playerBoard, Board enemyBoard, boolean hideEnemyShips) {
        System.out.println("\nВаше поле:");
        printBoard(playerBoard, false);
        System.out.println("\nПоле ворога:");
        printBoard(enemyBoard, hideEnemyShips);
    }

    private void printBoard(Board board, boolean hideShips) {
        System.out.print("  ");
        for (int i = 0; i < Board.SIZE; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();
        for (int i = 0; i < Board.SIZE; i++) {
            System.out.printf("%2d", i + 1);
            for (int j = 0; j < Board.SIZE; j++) {
                if (board.isHit(i, j)) {
                    System.out.print(" X");
                } else if (board.isShip(i, j) && !hideShips) {
                    System.out.print(" O");
                } else {
                    System.out.print(" ~");
                }
            }
            System.out.println();
        }
    }
}

