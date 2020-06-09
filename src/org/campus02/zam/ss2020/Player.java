package org.campus02.zam.ss2020;

import java.util.ArrayList;

public abstract class Player {
    private String name;
    private int points;
    private ArrayList<UnoCard> hand;
    private int cards;



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
