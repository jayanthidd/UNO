package org.campus02.zam.ss2020;

import java.util.ArrayList;

public abstract class Player {
    private String name;
    private int points;
    private ArrayList<UnoCard> hand;

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    } // mozemo dropati

    public ArrayList<UnoCard> getHand() {
        return hand;
    }

    public void setHand(ArrayList<UnoCard> hand) {
        this.hand = hand;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", points=" + points +
                ", hand=" + hand +
                '}';
    }

    public void setPoints(ArrayList<UnoCard> cardsLeft) { // int calculatePoints (treba biti u management)
        int pts = 0;
        for (UnoCard u : cardsLeft) {
            pts += u.getCardPoints();
        }
        this.points = pts;                              // return pts;
    }

    public boolean hasWon (String name) {
        if (this.points >= 500) {
            return true;
        }
        return false;
    }
}
