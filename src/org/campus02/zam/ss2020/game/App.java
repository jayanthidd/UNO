package org.campus02.zam.ss2020.game;
import org.campus02.zam.ss2020.players.HumanPlayer;
import org.campus02.zam.ss2020.players.Player;


import java.io.PrintStream;
import java.sql.SQLOutput;
import java.util.*;

public class App {
    UnoGame game = new UnoGame();
    private final Scanner input;
    private final PrintStream output;
    public static boolean roundEnded;
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
                    for (Player p : game.getPlayers()) {
                        game.setCurrentPlayer(p);
                        if (game.isCardsToBePickedUp()) {
                            game.pickUpCards();
                            continue;
                        }
                        if (game.isSkip()){
                            game.skip();
                            continue;
                        }
                        if (p.getName().contains("Robot")) {
                                game.robotPlays(p, game.getDiscardPile().peek());// need to write this method.  Currently, there is no logic.
                        } else {
                            printState();
                            readUserInput();
                            updateState();
                        }
                        if(p.getHand().isEmpty()){
                            break;
                        }
                    }
                    if (game.getCurrentPlayer().getHand().isEmpty()){
                        roundEnded();
                        break;
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
        //Create order of players --- completed
        //Create a Deck ----completed
        //Create Hands/Deal Cards/Set Hands for Players (Class UnoGame) - completed
    }

    private void initializeRound() {
        System.out.println("New Round Begins! ");
        roundEnded = false;
        game.reset();
        game.createNewDeck();
        Collections.shuffle(game.getDeckPile());
        //System.out.println(game.getDeckPile().size());
        game.dealCards();
        //System.out.println(game.getDeckPile().size());
        game.getDiscardPile().add(game.getDeckPile().pop());
    }

    private void readUserInput() {
        System.out.println("Your cards are : " + game.getCurrentPlayer().getHand());
        System.out.print("What card would you like to play? :");
        userInput = input.next().toUpperCase();
        System.out.println("-------------------------------------------------------------------");
    }

    private void updateState() {
        if (game.isCard(userInput)){
            game.processCard();
            return;
        }
        if(userInput.toUpperCase().contains("UNO")){
            if(game.checkUno()){
                readUserInput();
                updateState();
            }
        }else if (userInput.toUpperCase().equals("DRAW")){
            game.drawCard();
        }else if (userInput.toUpperCase().equals("SKIP")){
            return;
        } else if (userInput.toUpperCase().equals("HELP")) {
                game.printHelp();
                readUserInput();
                updateState();
        } else {
            System.out.println("Invalid entry");
            readUserInput();
            updateState();
        }
        // Read the user input (what will he play?) - completed
        // Validate the User input (can he play it?) - completed
        // Update the state and show the current print state - completed
    }

    private void printState() {
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Player " + game.getCurrentPlayer().getName().toUpperCase() + " plays!");
        if(game.getDiscardPile().peek().toString().contains("WILD")){
            System.out.println("The Open Card is : " + game.getDiscardPile().peek() + "   Color : " + game.getWildColor());
        } else {
            System.out.println("The Open Card is : " + game.getDiscardPile().peek());
        }
        // Print(show) the Top Card of the discard pile - completed
        // Print(show) the current hand of the player - completed

    }
    private void roundEnded(){
        game.completeRound();
        roundEnded = true;
    }

    private boolean gameEnded(){
        return false;
    }

    private void printFinalScore(){
        game.printPlayerScores();
    }
}
