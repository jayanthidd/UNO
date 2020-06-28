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
    private Player currentPlayer;

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
                printState();

                while (!roundEnded) {
                    for (Player p : game.players) {
                        currentPlayer = p;
                        System.out.println("Player " + p.getName() + " plays!");
                        System.out.println("Your cards are : " + p.getHand());
                        if (p.getName().contains("Robot")) {
                            game.robotPlays(p,game.discardPile.peek());// need to write this method properly.  Currently, there is no logic.
                        } else {
                            readUserInput();
                            updateState();
                        }
                        printState();
                        if (p.getHand().isEmpty()){
                            roundEnded=true;

                        }
                    }
                    readUserInput();
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

    //shuffle cards
    // Deal cards among players
    //create discard pile and open the first card
    }

    private void readUserInput() {
        if (game.discardPile.peek().toString().contains("DRAWTWO")||game.discardPile.peek().toString().contains("WILDFOUR") && game.isCardsPickedUp()!=false){
            return;
        }
        System.out.print("What card would you like to play? :");
        userInput = input.next();
    }

    private void updateState() {
        if(game.discardPile.peek().toString().contains("DRAWTWO")||game.discardPile.peek().toString().contains("WILDFOUR"))
            if (!game.isCardsPickedUp()) {
                game.pickUpCards(currentPlayer, game.discardPile.peek());
                game.setCardsPickedUp(true);
            }
        if(userInput.contains("UNO")){
            if(!game.checkUno(currentPlayer)){
                readUserInput();
            }
        }else {
            UnoCard currentCard = null;
            for (UnoCard c : currentPlayer.getHand()) {
                if (c.toString().equals(userInput)) {
                    currentCard = c;
                }
            }
            if(!game.processCard(currentPlayer, currentCard, game.discardPile.peek())){
                System.out.println("Not Allowed.  You miss a turn");
            }

            if (currentCard == null) {
                System.out.println("Invalid entry");
                readUserInput();
            }
        }
        // Read the user input (what will he play?)
        // Validate the User input (can he play it?)
        // Update the state and show the current print state

    }

    private void printState() {
        System.out.println("-------------------------------------------------------------------");
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
