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
            if (v.name().contains("WILD")){
                card = new UnoCard(v);
                deck.push(card);
                deck.push(card);
                deck.push(card);
                deck.push(card);
            }
            else {
                for (Type t : Type.values()) {
                    if (!v.name().contains("WILD")) {
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
        System.out.println();
    }

    @Override
    public String toString() {
        return "Deck{" +
                "deck=" + deck +
                '}';
    }

    public void shuffle () {
        ArrayList<String> sd = new ArrayList<>();
        Collections.shuffle(deck);
        System.out.println(deck);
    }
    public ArrayList<UnoCard> dealCards() {

        ArrayList<UnoCard> deal = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            UnoCard card = deck.pop();
            deal.add(card);
        }
        System.out.println(deck.size());
        return deal;
    }
}