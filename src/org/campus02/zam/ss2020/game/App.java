package org.campus02.zam.ss2020.game;
import org.campus02.zam.ss2020.SqliteClient.SqliteClient;
import org.campus02.zam.ss2020.exceptions.PlayerAlreadyExistsException;
import org.campus02.zam.ss2020.players.HumanPlayer;
import org.campus02.zam.ss2020.players.Player;


import java.io.PrintStream;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.*;

public class App {
    UnoGame game = new UnoGame();
    private final Scanner input;
    private final PrintStream output;
    public boolean roundEnded;
    public boolean gameEnded;
    private String userInput;
    public static int increment = 1;
    private SqliteClient client;
    private int round;
    private int session = 0;
    // adapt this line
    static final String CREATETABLE = "CREATE TABLE Sessions (Player varchar(100) NOT NULL, Session int NOT NULL, Round int NOT NULL, Score int NOT NULL, CONSTRAINT PK_Sessions PRIMARY KEY (Player, Session, Round));";
    //adapt this line for our game
    private static final String INSERT_TEMPLATE= "INSERT INTO Sessions (Player, Session, Round, Score) VALUES ('%1s', %2d, %3d, %4d);";
    //adapt this line.  Let's do this on Thursday
    private static final String SELECT_BYPLAYERANDSESSION = "SELECT Player, SUM(Score) AS Score FROM Sessions WHERE Player = '%1s' AND Session = %2d;";

    public App(Scanner input, PrintStream output){
        this.input = input;
        this.output = output;
        this.roundEnded = false;
        this.gameEnded = false;
    }

    public void Run() {
        try {
            initializeGame();
            do {
                initializeRound();
                while (!roundEnded) {

                    int initial = 0;
                    if (increment == -1) initial = 3;

                    for (int i = initial; i < 4 && i > -1; i+=increment) {
                        game.setPreviousPlayer(game.getCurrentPlayer());
                        game.setCurrentPlayer(game.getPlayers().get(i));
                        game.getPlayers().get(i).setUNOstatus(false);
                        if (game.isCardsToBePickedUp()) {
                            if(game.pickUpCards()) {
                                continue;
                            }
                        }
                        if (game.isSkip()){
                            game.skip();
                            continue;
                        } else {
                            printState();
                            readUserInput();
                            updateState();
                        }
                        if(game.getCurrentPlayer().getHand().isEmpty()){
                            break;
                        }
                    }
                    if (game.getCurrentPlayer().getHand().isEmpty()){
                        roundEnded();
                        break;
                    }
                }
                Thread.sleep(100);
            } while (!gameEnded);
            printFinalScore();
        }catch (Exception ex){
            output.println(ex);
        }
    }

    private void initializeGame() throws SQLException {
        round = 0;
        session ++;
        game.createPlayers();
        client = new SqliteClient("Unogame.sqlite");
        if (!client.tableExists("Sessions")){
            client.executeStatement(CREATETABLE);
        }
    }

    private void initializeRound() {
        System.out.println("New Round Begins! ");
        round++;
        roundEnded = false;
        game.reset();
    }

    private void readUserInput() {
        userInput = game.getCurrentPlayer().playCard(game.discardPile.peek(), game.getWildColor());
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
        }else if (userInput.toUpperCase().equals("POINTS")){
            game.printPlayerScores();
            readUserInput();
            updateState();
        } else if (userInput.toUpperCase().equals("HELP")) {
            game.printHelp();
            readUserInput();
            updateState();
        }
        else {
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
    private void roundEnded() throws SQLException {
        game.completeRound();
        roundEnded = true;
        //adapt this line for us
        for (Player p: game.getPlayers()) {
            client.executeStatement(String.format(INSERT_TEMPLATE, game.getCurrentPlayer().getName(), session, round, game.getCurrentPlayer().getPoints()));
        }
        for (Player p : game.getPlayers()){
            if(p.getPoints()>=500)
                gameEnded();
        }
    }

    private void gameEnded(){
        System.out.println();
        System.out.println("This game has ended!");
        gameEnded = true;
    }

    private void printFinalScore(){
        System.out.println();
        System.out.println("-------------------------------------------------------------");
        System.out.println("The final scores are : ");
        game.printPlayerScores();
    }
}
