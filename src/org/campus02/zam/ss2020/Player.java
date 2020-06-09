package org.campus02.zam.ss2020;

public abstract class Player {
    String name;
    int points;

    public Player(String name) {
        this.name = name;
        this.points = 0;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean hasWon (String name) {
        if (this.points >= 500) {
            return true;
        }
        return false;
    }
}
