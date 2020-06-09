package org.campus02.zam.ss2020;

import javax.accessibility.AccessibleRelation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck {
    Type[] type;
    Value[] value;
    Stack<String> deck;

    public Deck() {
        this.type =  Type.values();
        this.value = Value.values();
        this.deck = new Stack<>();
        String card;
        for (Type t : type) {
            if (t.name().contains("WILD")){
                card = t.name();
                deck.push(card);
                deck.push(card);
                deck.push(card);
                deck.push(card);

            }
            else {
                for (Value v : value) {
                    card = t.name() + "_" + v.name();
                    if(card.contains("ZERO")){
                        deck.push(card);

                    }else {
                        deck.push(card);
                        deck.push(card);
                    }
                }
            }
        }
    }

    public void shuffle () {
        ArrayList<String> sd = new ArrayList<>();
        Collections.shuffle(deck);
        //System.out.println(deck);
    }

    public ArrayList<String> dealCards() {

        ArrayList<String> deal = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            String card = deck.pop();
            deal.add(card);
        }
        //System.out.println(deck.size());
        return deal;
    }

    public static void main(String[] args) {
        Deck d = new Deck();
        d.shuffle();
        System.out.println(d.deck.size());
        System.out.println(d.dealCards());
    }
}
