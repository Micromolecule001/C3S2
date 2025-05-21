package org.example.logic;

import java.util.List;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Set;

public class Ship {
    private final int size;
    private int hits = 0;
    private final Set<String> coordinates = new HashSet<>();

    public Ship(int size) {
        this.size = size;
    }

    public void addCoordinate(int x, int y) {
        coordinates.add(x + "," + y);
    }

    public void hit() {
        hits++;
    }

    public boolean isSunk() {
        return hits >= size;
    }

    public List<int[]> getSurroundingCoordinates() {
    List<int[]> surrounding = new ArrayList<>();
    for (String coordStr : coordinates) {
        String[] parts = coordStr.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < 10 && ny >= 0 && ny < 10) {
                    boolean isPartOfShip = false;
                    for (String c : coordinates) {
                        String[] p = c.split(",");
                        int cx = Integer.parseInt(p[0]);
                        int cy = Integer.parseInt(p[1]);
                        if (cx == nx && cy == ny) {
                            isPartOfShip = true;
                            break;
                        }
                    }
                    if (!isPartOfShip) {
                        surrounding.add(new int[]{nx, ny});
                    }
                }
            }
        }
    }
    return surrounding;
}
} 

