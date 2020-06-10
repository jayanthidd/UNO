package org.campus02.zam.ss2020;

import java.io.PrintStream;
import java.util.Collections;
import java.util.Scanner;

public class App {
    UnoGame game = new UnoGame();
    private final Scanner input;
    private final PrintStream output;

    public App(Scanner input, PrintStream output){
        this.input = input;
        this.output = output;
    }



    public void Run() {
        initializeGame();
        try {
            do {
                initializeRound();
                printState();

                while (!roundEnded()) {
                    readUserInput();
                    updateState();
                    printState();

                    Thread.sleep(100);
                }
            } while (!gameEnded());

            printFinalScore();
        }catch (Exception ex){
            output.println(ex);
        }
    }

    private void initializeGame() {
    //receive player names DONE and create robots, if required (I dont know how)

        for (int i = 0; i< 4 ; i++) {
            System.out.print("Player Name: ");
            String Player = input.next();
            if (Player.equals("stop")){
                System.out.println("Bots will be added to complete the players!");
                break;
            }
            Player p = new HumanPlayer(Player);
            game.addPlayer(p);
        }
        game.completePlayers();
        Collections.shuffle(game.players);
        System.out.println("This is the order in which players will play :");
        System.out.println(game.players);


        //Create order of players --- needs to be shuffled
        //Create a Deck (its on Class UnoGame)
        //Create Hands/Deal Cards/Set Hands for Players (Class UnoGame)
    }

    private void initializeRound() {
        Collections.shuffle(game.deckPile);
        game.dealCards();
        System.out.println();
        game.discardPile.add(game.deckPile.pop());
        while (!roundEnded()) {
            for (Player p : game.players) {
                System.out.println("Player " + p.getName() + " plays!");
                System.out.println("Your cards are : " + p.getHand());
                printState();
                updateState();
            }
        }
    //shuffle cards
    // Deal cards among players
    //create discard pile and open the first card
    }

    private void readUserInput() {

    }

    private void updateState() {
        readUserInput();
        // Read the user input (what will he play?)
        // Validate the User input (can he play it?)
        // Update the state and show the current print state

    }

    private void printState() {

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

    }
}
