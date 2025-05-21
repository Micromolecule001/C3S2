package org.example.logic;

import org.example.ui.ConsoleUI;

import java.util.Random;

public class Game {
    private final Player human;
    private final AIPlayer computer;
    private final ConsoleUI ui;
    private boolean playerTurn;

    public Game() {
        this.human = new Player("Гравець");
        this.computer = new AIPlayer("Комп'ютер");
        this.ui = new ConsoleUI();
        this.playerTurn = new Random().nextBoolean();
    }

    public void start() {
        human.getBoard().placeAllShipsRandomly();
        computer.getBoard().placeAllShipsRandomly();

        ui.printMessage("Гру розпочато! " + (playerTurn ? "Гравець" : "Комп'ютер") + " починає першим.\n");

        while (!human.getBoard().allShipsSunk() && !computer.getBoard().allShipsSunk()) {
            if (playerTurn) {
                ui.printBoards(human.getBoard(), computer.getBoard(), true);
                int[] shot = ui.askForCoordinates();
                boolean hit = computer.getBoard().shootAt(shot[0], shot[1]);
                ui.printShotResult(hit, computer.getBoard(), shot[0], shot[1]);
                if (!hit) playerTurn = false;
            } else {
                int[] shot = computer.makeMove(human.getBoard());
                boolean hit = human.getBoard().shootAt(shot[0], shot[1]);
                ui.printMessage("Комп'ютер стріляє в координати: " + (char)('A' + shot[1]) + (shot[0] + 1));
                ui.printShotResult(hit, human.getBoard(), shot[0], shot[1]);
                if (!hit) playerTurn = true;
            }
        }

        ui.printMessage("Гру завершено! Переможець: " + (human.getBoard().allShipsSunk() ? "Комп'ютер" : "Гравець"));
    }
}

