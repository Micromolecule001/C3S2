package org.example.logic;

public enum Choice {
    ROCK, PAPER, SCISSORS;

    public static Choice random() {
        Choice[] choices = values();
        return choices[(int) (Math.random() * choices.length)];
    }

    public boolean beats(Choice other) {
        return (this == ROCK && other == SCISSORS) ||
               (this == PAPER && other == ROCK) ||
               (this == SCISSORS && other == PAPER);
    }
}

