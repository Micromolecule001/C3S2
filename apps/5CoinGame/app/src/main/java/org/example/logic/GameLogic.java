package org.example.logic;

import java.util.Random;

public class GameLogic {
    private int coins;
    private boolean isUserTurn;
    private final Random random = new Random();

    public GameLogic() {
        this.coins = 10 + random.nextInt(11); // 10 to 20 coins
        this.isUserTurn = random.nextBoolean(); // random first turn
    }

    public int getCoins() {
        return coins;
    }

    public boolean isUserTurn() {
        return isUserTurn;
    }

    public void userMove(int taken) {
        if (taken < 1 || taken > 2 || taken > coins) return;
        coins -= taken;
        isUserTurn = false;
    }

    public int computerMove() {
        int taken;
        if (coins % 3 == 0) {
            taken = 2;
        } else if (coins % 3 == 1) {
            taken = 1;
        } else {
            taken = 1;
        }

        if (coins == 1) taken = 1;
        else if (coins == 2) taken = 2;

        coins -= taken;
        isUserTurn = true;
        return taken;
    }

    public boolean isGameOver() {
        return coins <= 0;
    }

    public String getWinner() {
        return isUserTurn ? "Комп’ютер" : "Користувач";
    }

    public void reset() {
        this.coins = 10 + random.nextInt(11);
        this.isUserTurn = random.nextBoolean();
    }
}

