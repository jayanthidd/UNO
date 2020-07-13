package org.campus02.zam.ss2020.players;

import org.campus02.zam.ss2020.cards.UnoCard;
import org.campus02.zam.ss2020.game.UnoGame;
import org.campus02.zam.ss2020.players.Player;

import java.util.Scanner;

public class HumanPlayer extends Player {
    private UnoCard playedCard;
    private String userInput;
    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public String playCard() {
        Scanner input = new Scanner(System.in);
        System.out.println("Your cards are : " + getHand());
        System.out.print("What card would you like to play? :");
        return input.next().toUpperCase();
    }
}
