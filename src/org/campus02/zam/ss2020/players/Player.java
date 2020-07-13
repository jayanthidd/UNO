package org.campus02.zam.ss2020.players;

import org.campus02.zam.ss2020.cards.UnoCard;
import org.campus02.zam.ss2020.game.UnoGame;

import java.util.ArrayList;

public abstract class Player {
    private String name;
    private int points;
    private ArrayList<UnoCard> hand;
    private boolean UNOstatus;

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.hand = new ArrayList<>();
        this.UNOstatus = false;
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

    public abstract void playCard();

    @Override
    public String toString() {
        return "Player Name =" + name;

    }

    public void setPoints(ArrayList<UnoCard> cardsLeft) { // int calculatePoints (treba biti u management)
        int pts = 0;
        for (UnoCard u : cardsLeft) {
            pts += u.getCardPoints();
        }
        this.points = pts;                              // We will need to write SQL code to connect to the database
    }

    public boolean hasWon (String name) {
        if (this.points >= 500) {
            return true;
        }
        return false;
    }

    public boolean isUNOstatus() {
        return UNOstatus;
    }

    public void setUNOstatus(boolean UNOstatus) {
        this.UNOstatus = UNOstatus;
    }
}
