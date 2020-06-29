package org.campus02.zam.ss2020.game;

import org.campus02.zam.ss2020.cards.Type;
import org.campus02.zam.ss2020.cards.UnoCard;
import org.campus02.zam.ss2020.players.HumanPlayer;
import org.campus02.zam.ss2020.players.Player;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Scanner;

public class App {
    UnoGame game = new UnoGame();
    private final Scanner input;
    private final PrintStream output;
    private boolean roundEnded;
    private String userInput;


    public App(Scanner input, PrintStream output){
        this.input = input;
        this.output = output;
        this.roundEnded = false;
    }

    public void Run() {
        initializeGame();
        try {
            do {
                initializeRound();
                while (!roundEnded) {
                    for (Player p : game.players) {
                        game.setCurrentPlayer(p);
                        if (game.isCardsToBePickedUp()) {
                            game.pickUpCards();
                            continue;
                        }
                        if (p.getName().contains("Robot")) {
                                game.robotPlays(p, game.discardPile.peek());// need to write this method properly.  Currently, there is no logic.
                        } else {
                            printState();
                            readUserInput();
                            updateState();
                        }
                        if (p.getHand().isEmpty()) {
                            roundEnded = true;
                        }
                    }
                }
                Thread.sleep(100);
            } while (!gameEnded());
            printFinalScore();
        }catch (Exception ex){
            output.println(ex);
        }
    }

    private void initializeGame() {
    //receive player names DONE and create robots, if required (I dont know how)
        game.createPlayers();
        //Create order of players --- needs to be shuffled
        //Create a Deck (its on Class UnoGame)
        //Create Hands/Deal Cards/Set Hands for Players (Class UnoGame)
    }

    private void initializeRound() {
        Collections.shuffle(game.deckPile);
        game.dealCards();
        System.out.println();
        game.discardPile.add(game.deckPile.pop());

    //shuffle cards
    // Deal cards among players
    //create discard pile and open the first card
    }

    private void readUserInput() {
        userInput = input.next();
    }

    private void updateState() {
        if(userInput.contains("UNO")){
            if(!game.checkUno()){
                readUserInput();
            }
        }else if (userInput.equals("DRAW")){
            game.drawCard();
            readUserInput();
            updateState();
        }else {
            UnoCard currentCard = null;
            for (UnoCard c : game.getCurrentPlayer().getHand()) {
                if (c.toString().equals(userInput)) {
                    currentCard = c;
                }
            }
            if (currentCard == null) {
                System.out.println("Invalid entry");
                readUserInput();
            }
            game.processCard(currentCard, game.discardPile.peek());
        }
        // Read the user input (what will he play?)
        // Validate the User input (can he play it?)
        // Update the state and show the current print state

    }

    private void printState() {
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Player " + game.getCurrentPlayer().getName() + " plays!");
        System.out.println("Your cards are : " + game.getCurrentPlayer().getHand());
        System.out.println("The Open Card is : " + game.discardPile.peek());
        System.out.print("What card would you like to play? :");
        // Print(show) the Top Card of the discard pile
        // Print(show) the current hand of the player

    }

    private boolean roundEnded(){
        return false;
    }

    private boolean gameEnded(){
        return false;
    }

    private void printFinalScore(){
        System.out.println(game.playerScores());
    }
}
