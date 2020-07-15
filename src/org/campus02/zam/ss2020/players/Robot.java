package org.campus02.zam.ss2020.players;

import org.campus02.zam.ss2020.cards.Deck;
import org.campus02.zam.ss2020.cards.UnoCard;
import org.campus02.zam.ss2020.game.UnoGame;
import org.campus02.zam.ss2020.players.Player;

import java.util.Stack;

public class Robot extends Player {

    public Robot(String name) {
        super(name);
    }

    @Override
    public String playCard(UnoCard playedCard, String wildColor) {
        System.out.println("Your cards are : " + getHand());
        System.out.print("What card would you like to play? :");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (getHand().size()==2 && !isUNOstatus()) {
            System.out.println("UNO");
            return "UNO";
        }
        if (playedCard.toString().contains("WILD")){
            for (UnoCard c : getHand()){
                if (c.toString().contains(wildColor)){
                    System.out.println(c.toString());
                    return c.toString();
                }
            }
        }
        for (UnoCard c : getHand()) {
            if (c.value.equals(playedCard.value) || c.type.equals(playedCard.type)) {
                System.out.println(c.toString());
                return c.toString();
            }
        }
            for (UnoCard w : getHand()) {
                if (w.toString().contains("WILD")) {
                    System.out.println(w.toString());
                    return w.toString();
                }
            }
        System.out.println("DRAW");
        return "DRAW";
    }

    @Override
    public String playWild() {
        for (UnoCard c : getHand()) {
            if (!c.type.toString().contains("WILD")) {
                System.out.println(c.type.toString());
                return c.type.toString();
            }
        }
        System.out.println("GREEN");// we need to see if we can give out random colors
        return "GREEN";
    }
}
