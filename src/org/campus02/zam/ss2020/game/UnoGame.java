package org.campus02.zam.ss2020.game;

import org.campus02.zam.ss2020.cards.Deck;
import org.campus02.zam.ss2020.cards.Type;
import org.campus02.zam.ss2020.cards.UnoCard;
import org.campus02.zam.ss2020.exceptions.PlayerAlreadyExistsException;
import org.campus02.zam.ss2020.players.HumanPlayer;
import org.campus02.zam.ss2020.players.Player;
import org.campus02.zam.ss2020.players.Robot;

import java.util.*;

/**
 * This class manages the entire game.  All the rules of the game are validated here. This class has access to all other classes within the UNO project
 * Data Structures used:
 * deckpile : Stack - no search operations performed and we need to always open only the top card.
 * discardpile : Stack - no search operations performed and we need to always only add a card at the top and look at the top card.
 * players : LinkedList - no search operations performed but need to be able to easily skip and reverse the order
 */
public class UnoGame {
    private ArrayList<Player> players;// linkedlist
    protected Stack<UnoCard> discardPile;
    protected Stack<UnoCard> deckPile;
    private String wildColor;
    private boolean cardsToBePickedUp;
    private Player currentPlayer;
    private Player previousPlayer;
    private UnoCard playedCard;
    private boolean skip;
    private UnoCard previousCard;
    private Scanner scanner;

    public UnoGame() {
        this.players = new ArrayList<>();
        this.discardPile = new Stack<>();
        this.deckPile = new Stack<>();
        this.cardsToBePickedUp = false;
        this.skip = false;
        scanner = new Scanner(System.in);
    }

    public void createNewDeck(){
        deckPile = new Deck().deck;
    }

    public void processCard() {
        if (currentPlayer.getHand().size()==2){
            if (!currentPlayer.isUNOstatus()){
                System.out.println("You did not say UNO");
                penalty(1);
                return;
            }
        }
        if (isAllowed()) {
            updatePlayedCard();
            if (playedCard.toString().contains("WILD")) {
                allowWild();
            }
            if (playedCard.toString().contains("REVERSE")) {
                App.increment *= -1;
            }
            if (playedCard.toString().contains("SKIP")){
                skip = true;
            }
        } else {
            System.out.println("You cannot play that card! Penalty!");
            penalty(1);
        }
        if (playedCard.toString().equals("WILDPLUS4") || playedCard.toString().contains("DRAWTWO")) {
            cardsToBePickedUp = true;
        }
    }

    private void updatePlayedCard()  {
        previousCard = discardPile.peek();
        discardPile.push(playedCard);
        currentPlayer.getHand().remove(playedCard);
        renewDeckPile();

    }

    public void completeRound() {
        currentPlayer.setPoints(combineHandsFromAllPlayers());
        System.out.println();
        System.out.println("This round is over!");
        System.out.println("Congratulations " + currentPlayer.getName());
        System.out.println();
        printPlayerScores();
        System.out.println();
    }

    private void allowWild() {
        System.out.print("What color would you like to change to? : ");

        String color = currentPlayer.playWild();

        if (color.equals(Type.GREEN.toString())|| color.equals(Type.BLUE.toString())||color.equals(Type.RED.toString())|| color.equals(Type.YELLOW.toString())) {//checking if the color entered by the user is valid
            wildColor = color;
        } else {
            System.out.println("Invalid Entry!");
            allowWild();
        }
    }

    /**
     * checks if the card played is allowed based on the type and the value of the card
     * @return
     */
    public boolean isAllowed() {
        if (playedCard.value == discardPile.peek().value || playedCard.type == discardPile.peek().type || playedCard.toString().contains("WILD")) {
            return true;
        }
        if (discardPile.peek().toString().contains("WILD") && playedCard.toString().contains("WILD")){
            if (currentPlayer.getHand().contains(wildColor)){
                return false;
            } else {
                return true;
            }
        }
        if (discardPile.peek().toString().contains("WILD")) {
            return validateWild();
        }
        return false;
    }


    /**
     * WHen the open card is a wild card, this method checks to see if the card played by the player is valid.
     *
     * @return
     */
    private boolean validateWild() {
        if (discardPile.size() == 1) {
            return true;
        } else if (playedCard.type.toString().equals(wildColor)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean pickUpCards() {
        if (discardPile.peek().toString().contains("DRAWTWO")) {
            penalty(2);
            printCardsPickedUp();
            return true;
        } else if (discardPile.peek().toString().contains("WILDPLUS4") && discardPile.size()!=1) {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("Player " + currentPlayer.getName() + " plays!");
            System.out.println("The Open Card is : " + discardPile.peek());
            System.out.print("Would you like to challenge this card? Y / N : ");
            if (currentPlayer.getName().contains("Robot")){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("N");
                penalty(4);printCardsPickedUp();
                return true;
            }
            String response = scanner.next().toUpperCase();
            if (response.equals("Y")){
                return checkChallenge();
            } else {
                penalty(4);
                printCardsPickedUp();
                return true;
            }
        }
//        else if (discardPile.peek().toString().contains("WILDPLUS4")){
//            penalty(4);
//            printCardsPickedUp();
//            return true;
//        }
        return true;
    }

    private void printCardsPickedUp() {
        System.out.println("-------------------------------------------------------------------");
        System.out.println("The Open Card is : " + discardPile.peek());
        System.out.println(currentPlayer.getName() + " has picked up cards and has missed a turn");
        System.out.println(currentPlayer.getName() + "'s cards are : " + currentPlayer.getHand());
        cardsToBePickedUp = false;
    }

    public void renewDeckPile() {
        if (deckPile.size()<=4) {
            UnoCard topCard = discardPile.pop();
            deckPile.addAll(discardPile);
            discardPile.removeAllElements();
            Collections.shuffle(deckPile);
            discardPile.push(topCard);
        }
    }

    public void dealCards() {
        for (Player p : players) {
            ArrayList<UnoCard> deal = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                UnoCard card = deckPile.pop();
                deal.add(card);
            }
            p.setHand(deal);
        }
    }

    public void penalty(int cards) {
        ArrayList<UnoCard> hand = currentPlayer.getHand();
        for (int i = 0; i < cards; i++) {
            hand.add(deckPile.pop());
        }
        currentPlayer.setHand(hand);
    }

    public void completePlayers() {

        int size = players.size();
        if (size == 4) {
            System.out.println("There are 4 players");
        } else {
            for (int i = size; i < 4; i++) {
                players.add(new Robot(String.format("Robot%2d", i)));
                System.out.println();
            }
        }
    }

    public void printPlayerScores() {
        System.out.println("Scores are ");
        for (Player p : players) {
            System.out.println(p.getName() + " : " + p.getPoints());
        }
    }

    public ArrayList<UnoCard> combineHandsFromAllPlayers() {
        ArrayList<UnoCard> combinedHand = new ArrayList<>();
        for (Player p : players) {
            combinedHand.addAll(p.getHand());
        }
        return combinedHand;
    }

    /**
     * This method checks if the player is allowed to say Uno at this point.  No penatlty for incorrect call
     *
     * @return
     */
    public boolean checkUno() {
        for (Player p : players) {
            if (p.equals(currentPlayer)) {
                if (p.getHand().size() == 2) {
                    p.setUNOstatus(true);
                    System.out.println(p.getName() + " said UNO");
                    return true;
                } else {
                    System.out.println("You can say Uno only when you have 2 cards left!");
                    return false;
                }
            }
        }
        return false;
    }

    public void skip(){
        skip = false;
        System.out.println(currentPlayer.getName() + " misses a turn");
    }

    public boolean isSkip() {
        return skip;
    }

    public void printHelp() {
        System.out.println("Type in the name of the card you wish to play!");
        System.out.println("DRAW - Take a new card to play");
        System.out.println("POINTS - Score of all players");
    }

    public void drawCard() {
        playedCard = deckPile.pop();
        currentPlayer.getHand().add(playedCard);
        if (isAllowed()){
            System.out.println("Your new card " + playedCard + " has been played");
            processCard();
            updatePlayedCard();
        } else {
            System.out.println("You cannot play the new card!");
            System.out.println("Your new cards are : " + currentPlayer.getHand());
            renewDeckPile();
        }
        return;
    }

    public void createPlayers() {
        System.out.println("Please enter your names.  There should be atleast one human player");
        System.out.println("Please type 'STOP' when you want the program to add bots");
        while (players.size()!=4) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("Player Name: ");
            String Player = scanner.next().toUpperCase();
            for (Player pl : players){
                if(pl.getName().equals(Player)) {
                    System.out.println("Player already exists");
                    try {
                        throw new PlayerAlreadyExistsException();
                    } catch (PlayerAlreadyExistsException e) {
                        e.printStackTrace();
                    } finally {
                        Player = null;
                    }
                }
            }
            if(Player==null) {
                continue;
            }
            if (Player.equals("STOP")) {
//                if (players.size() == 0) {
//                    System.out.println("There should be at least one human player");
//                    createPlayers();
//                }
                System.out.println("Bots will be added to complete the players!");
                break;
            }
            Player p = new HumanPlayer(Player);
            players.add(p);
        }
        completePlayers();
        Collections.shuffle(players);
        System.out.println();
        System.out.println("This is the order in which players will play :");
        System.out.println(players);
        System.out.println();
    }

    public boolean isCard(String userInput) {
        for (UnoCard c : currentPlayer.getHand()) {
            if (c.toString().equals(userInput)) {
                playedCard = c;
                return true;
            }
        }
        return false;
    }

    public boolean isCardsToBePickedUp() {
        return cardsToBePickedUp;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Stack<UnoCard> getDiscardPile() {
        return discardPile;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Stack<UnoCard> getDeckPile() {
        return deckPile;
    }

    public String getWildColor() {
        return wildColor;
    }

    public void reset() {
        cardsToBePickedUp = false;
        skip = false;
        currentPlayer = null;
        playedCard = null;
        wildColor = null;
        createNewDeck();
        Collections.shuffle(getDeckPile());
        dealCards();
        getDiscardPile().add(getDeckPile().pop());
        if (discardPile.peek().toString().equals("WILDPLUS4") || discardPile.peek().toString().contains("DRAWTWO")) {
            cardsToBePickedUp = true;
        }
    }

    public boolean checkChallenge() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (UnoCard c : previousPlayer.getHand()){
                if(c.type.equals(previousCard.type)||c.value.equals(previousCard.value)){
                    System.out.println("The previous player played a wrong card and has picked up the 4 cards as penalty");
                    for(int i =0; i<4; i++)
                        previousPlayer.getHand().add(deckPile.pop());
                    previousPlayer.getHand().add(discardPile.pop());
                    cardsToBePickedUp=false;
                    return false;
                }
            }
            System.out.println("False Challenge. 6 cards have been added to your hand and you will miss a turn");
            for(int i = 0; i<6; i++) {
                currentPlayer.getHand().add(deckPile.pop());
            }
            cardsToBePickedUp = false;
            return true;
    }

    public void setPreviousPlayer(Player previousPlayer) {
        this.previousPlayer = previousPlayer;
    }
}
