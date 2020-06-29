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
        game.createPlayers();
        //Create order of players --- needs to be shuffled
        //Create a Deck (its on Class UnoGame)
        //Create Hands/Deal Cards/Set Hands for Players (Class UnoGame) - we are doing this part in Initialise round!
    }

    private void initializeRound() {
        Collections.shuffle(game.deckPile);
        game.dealCards();
        System.out.println();
        game.discardPile.add(game.deckPile.pop());
    }

    private void readUserInput() {
        System.out.println("Your cards are : " + game.getCurrentPlayer().getHand());
        System.out.print("What card would you like to play? :");
        userInput = input.next();
    }

    private void updateState() {
        if (game.validCard(userInput)){
            game.processCard();
            return;
        }
        if(userInput.contains("UNO")){
            if(!game.checkUno()){
                readUserInput();
            }
        }else if (userInput.equals("DRAW")){
            game.drawCard();
            readUserInput();
            updateState();
        }else if (userInput.equals("SKIP")){
            return;
        } else {
            System.out.println("Invalid entry");
            readUserInput();
            updateState();
        }
        // Read the user input (what will he play?)
        // Validate the User input (can he play it?)
        // Update the state and show the current print state

    }

    private void printState() {
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Player " + game.getCurrentPlayer().getName() + " plays!");
        System.out.println("The Open Card is : " + game.discardPile.peek());
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
