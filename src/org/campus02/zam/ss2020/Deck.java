package org.campus02.zam.ss2020;

import javax.accessibility.AccessibleRelation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck {
    Type[] type;
    Value[] value;
    Stack<UnoCard> deck;

    public Deck() {
        this.type =  Type.values();
        this.value = Value.values();
        this.deck = new Stack<>();
        UnoCard card;
        for (Value v : value){
            if (v.name().contains("WILD")){
                card = new UnoCard(v);
                deck.push(card);
                deck.push(card);
                deck.push(card);
                deck.push(card);
            }
            else {
                for (Type t : type) {
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
//        for (Value v : value) {
//
//                 if (v.name().contains("WILD")) {
//                     card = new UnoCard(v);
//                     deck.push(card);
//                     deck.push(card);
//                     deck.push(card);
//                     deck.push(card);
//                 }
//                 else{
//                     for (Type t : type) {
//                 card = new UnoCard(t, v);
//                     if (card.equals("ZERO")) {
//                         deck.push(card);
//
//                     } else {
//                         deck.push(card);
//                         deck.push(card);
//                     }
//                 }
//             }
//        }
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
