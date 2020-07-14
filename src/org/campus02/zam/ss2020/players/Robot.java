package org.campus02.zam.ss2020.players;

import org.campus02.zam.ss2020.cards.Deck;
import org.campus02.zam.ss2020.cards.UnoCard;
import org.campus02.zam.ss2020.game.UnoGame;
import org.campus02.zam.ss2020.players.Player;

import java.util.Stack;

public class Robot extends Player {

    private UnoCard playedCard;
    private Deck deck;
    UnoGame game;


    public Robot(String name) {
        super(name);
    }

    @Override
    public String playCard() {
        for (UnoCard c : getHand()) {
            if (c.value.equals(playedCard.value) || c.type.equals(playedCard.type)) {
                return "Karte gespielt";
            } else {
                for (UnoCard w : getHand()) {
                    if (w.toString().contains("WILD")) {
                        return "Karte gespielt";
                    }
                }
            }if (!c.value.equals(playedCard.value) || !c.type.equals(playedCard.type)){
                game.drawCard();
            }
        }
        return null;
    }
}
