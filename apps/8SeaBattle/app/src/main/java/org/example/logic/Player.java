package org.example.logic;

public class Player {
    private final String name;
    private final Board board;

    public Player(String name) {
        this.name = name;
        this.board = new Board();
    }

    public String getName() {
        return name;
    }

    public Board getBoard() {
        return board;
    }
}

