package org.example.logic;

public class Cell {
    private Ship ship;
    private boolean hit = false;

    public void placeShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public boolean shoot() {
        if (hit) return false;
        hit = true;
        if (ship != null) {
            ship.hit();
            return true;
        }
        return false;
    }

    public boolean hasShip() {
        return ship != null;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isEmpty() {
        return ship == null;
    }
}
