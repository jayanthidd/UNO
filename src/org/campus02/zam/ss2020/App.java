package org.campus02.zam.ss2020;

import java.io.PrintStream;
import java.util.Scanner;

public class App {
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

        System.out.println("Player number one Name: ");
        String firstPlayer = input.next();
        System.out.println("Player number two Name: ");
        String secondPlayer = input.next();
        System.out.println("Player number three Name: ");
        String thirdPlayer = input.next();
        System.out.println("Player number four Name: ");
        String fourthPlayer = input.next();

        System.out.println("the players are: 1) " +firstPlayer + " 2) " +secondPlayer + " 3) " +thirdPlayer + " 4) " +fourthPlayer);

        //Create order of players --- needs to be shuffled
        //Create a Deck (its on Class UnoGame)
        //Create Hands/Deal Cards/Set Hands for Players (Class UnoGame)

    }

    private void initializeRound() {
    //shuffle cards
    // Deal cards among players
    //create discard pile and open the first card
    }

    private void readUserInput() {

    }

    private void updateState() {

        // Read the user input (what will he play?)
        // Validate the User input (can he play it?)
        // Update the state and show the current print state

    }

    private void printState() {
        System.out.println();

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
