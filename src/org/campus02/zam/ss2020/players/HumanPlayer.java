package org.campus02.zam.ss2020.players;

import org.campus02.zam.ss2020.cards.UnoCard;

import java.util.Scanner;

public class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public String playCard(UnoCard playedCard, String wildColor) {
        Scanner input = new Scanner(System.in);
        System.out.println("Your cards are : " + getHand());
        System.out.print("What card would you like to play? :");
        return input.next().toUpperCase();
    }

    @Override
    public String playWild() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next().toUpperCase();

    }
}
