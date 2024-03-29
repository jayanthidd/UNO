package org.campus02.zam.ss2020.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck {
    private Type[] type;
    private Value[] value;
    public Stack<UnoCard> deck;

    public Deck() {
        this.type =  Type.values();
        this.value = Value.values();
        this.deck = new Stack<>();
        UnoCard card;
        for (Value v : Value.values()){

                for (Type t : Type.values()) {

                    if (t.name().contains("WILD")){
                        if (v.name().contains("COLOR")||v.name().contains("PLUS4")) {
                            card = new UnoCard(t, v);
                            deck.push(card);
                            deck.push(card);
                            deck.push(card);
                            deck.push(card);
                        }
                    } else if (!v.name().contains("COLOR") && !v.name().contains("PLUS4")) {
                        if (v.name().contains("ZERO")) {
                            card = new UnoCard(t, v);
                            deck.push(card);
                        } else {
                            card = new UnoCard(t, v);
                            deck.push(card);
                            deck.push(card);
                        }
                    }

                }

        }
    }

    @Override
    public String toString() {
        return "Deck{" +
                "deck=" + deck +
                '}';
    }

    public void shuffle () {
        Collections.shuffle(deck);
    }
}
