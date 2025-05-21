package org.example.logic;

import java.util.Random;

public class AIPlayer extends Player {
    private final Random random = new Random();

    public AIPlayer(String name) {
        super(name);
    }

    public int[] makeMove(Board opponentBoard) {
        int x, y;
        do {
            x = random.nextInt(Board.SIZE);
            y = random.nextInt(Board.SIZE);
        } while (opponentBoard.getGrid()[x][y].isHit());
        return new int[]{x, y};
    }
}

