package org.example.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public static final int SIZE = 10;
    private final Cell[][] grid = new Cell[SIZE][SIZE];
    private final List<Ship> ships = new ArrayList<>();

    public Board() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public boolean placeShip(int x, int y, int length, boolean horizontal) {
        if (!canPlaceShip(x, y, length, horizontal)) return false;

        Ship ship = new Ship(length);
        for (int i = 0; i < length; i++) {
            int xi = x + (horizontal ? 0 : i);
            int yi = y + (horizontal ? i : 0);
            grid[xi][yi].placeShip(ship);
            ship.addCoordinate(xi, yi);
        }
        ships.add(ship);
        return true;
    }

    private boolean canPlaceShip(int row, int col, int size, boolean horizontal) {
        if (horizontal) {
            if (col + size > SIZE) return false;
        } else {
            if (row + size > SIZE) return false;
        }
    
        for (int i = -1; i <= size; i++) {
            for (int j = -1; j <= 1; j++) {
                int r = row + (horizontal ? j : i);
                int c = col + (horizontal ? i : j);
                if (r >= 0 && r < SIZE && c >= 0 && c < SIZE) {
                    if (!grid[r][c].isEmpty()) return false; // <-- виправлено: grid замість cells
                }
            }
        }
    
        return true;
    }    

    public void placeAllShipsRandomly() {
        int[] shipLengths = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        Random rand = new Random();
        for (int length : shipLengths) {
            boolean placed = false;
            while (!placed) {
                int x = rand.nextInt(SIZE);
                int y = rand.nextInt(SIZE);
                boolean horizontal = rand.nextBoolean();
                placed = placeShip(x, y, length, horizontal);
            }
        }
    }

    public boolean shootAt(int x, int y) {
        boolean wasHit = grid[x][y].shoot();

        if (wasHit) {
            Ship ship = grid[x][y].getShip();
            if (ship != null && ship.isSunk()) {
                for (int[] coord : ship.getSurroundingCoordinates()) {
                    int nx = coord[0];
                    int ny = coord[1];
                    if (!grid[nx][ny].isHit()) {
                        grid[nx][ny].shoot(); // позначаємо як промах
                    }
                }
            }
        }

        return wasHit;
    }

    public boolean allShipsSunk() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) return false;
        }
        return true;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public boolean isHit(int x, int y) {
        return grid[x][y].isHit();
    }

    public boolean isShip(int x, int y) {
        return grid[x][y].hasShip();
    }
} 

