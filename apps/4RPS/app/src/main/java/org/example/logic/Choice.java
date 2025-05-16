package org.example.logic;

public enum Choice {
    ROCK("Камень"),
    PAPER("Бумага"),
    SCISSORS("Ножницы");

    private final String displayName;

    Choice(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        System.out.println("Into getDisplayName: " + displayName);
        return displayName;
    }
}
