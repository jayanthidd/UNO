package org.campus02.zam.ss2020.game;

import org.campus02.zam.ss2020.cards.Deck;
import org.campus02.zam.ss2020.cards.Type;
import org.campus02.zam.ss2020.cards.UnoCard;
import org.campus02.zam.ss2020.players.HumanPlayer;
import org.campus02.zam.ss2020.players.Player;
import org.campus02.zam.ss2020.players.Robot;

import java.util.*;

/**
 * Data Structures used:
 * deckpile : Stack - no search operations performed and we need to always open only the top card.
 * discardpile : Stack - no search operations performed and we need to always open only the top card.
 * players : LinkedList - no search operations performed but need to be able to easily skip and reverse the order
 */
public class UnoGame {
    private ArrayList<Player> players;// linkedlist
    private Stack<UnoCard> discardPile;
    private Stack<UnoCard> deckPile;
    public String wildColor;
    private boolean cardsToBePickedUp;
    private Player currentPlayer;
    private UnoCard playedCard;
    private boolean skip;

    public UnoGame() {
        this.players = new ArrayList<>();
        this.discardPile = new Stack<>();
        this.deckPile = new Deck().deck;
        this.cardsToBePickedUp = false;
        this.skip = false;
    }

    public void addPlayer(Player p) {
        if (players.contains(p)) {
            System.out.println("Player already exists! Try another name!");
        } else {
            players.add(p);
        }
    }

    public void processCard() {
        if (isAllowed()) {
            updatePlayedCard();
            if (playedCard.toString().contains("WILD")) {
                allowWild();
            }
            if (playedCard.toString().contains("REVERSE")) {
                reverse();
            }
        } else {
            System.out.println("You cannot play that card! Penalty!");
            penalty(1);
        }
        if (playedCard.toString().equals("WILDFOUR") || playedCard.toString().contains("DRAWTWO")) {
            cardsToBePickedUp = true;
        }
    }

    private void updatePlayedCard() {
        discardPile.push(playedCard);
        if (currentPlayer.getHand().size() != 1) {
            currentPlayer.getHand().remove(playedCard);
        } else {
            completeRound();
        }
    }

    private void completeRound() {
        currentPlayer.setPoints(combineHandsFromAllPlayers());
        System.out.println("This round is over!");
        System.out.println("Congratulations " + currentPlayer.getName());
        printPlayerScores();
    }

    private void allowWild() {
        System.out.print("What color would you like to change to? : ");
        Scanner scanner = new Scanner(System.in);
        String color = scanner.next();
        if (Arrays.toString(Type.values()).contains(color)) {//checking if the color entered by the user is valid
            wildColor = color;
        } else {
            System.out.println("Invalid Entry!");
            allowWild();
        }
    }

    public boolean isAllowed() {
        if (discardPile.peek().toString().contains("WILD")) {
            return validateWild();
        }
        if (playedCard.toString().contains("WILD")) {
            return isWildAllowed();
        } else if (playedCard.value == discardPile.peek().value || playedCard.type == discardPile.peek().type) {
            return true;
        } else
            return false;
    }

    /**
     * checks if the current player is allowed to play a wild card
     *
     * @return
     */
    private boolean isWildAllowed() {
        if (currentPlayer.getHand().contains(discardPile.peek().value) || currentPlayer.getHand().contains(discardPile.peek().type)) {
            penalty(1);
            System.out.println("You can't play that card. Penalty!");
            return false;
        } else return true;
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

    public void pickUpCards() {
        if (discardPile.peek().toString().contains("DRAWTWO")) {
            penalty(2);
        } else if (discardPile.peek().toString().contains("WILDFOUR")) {
            penalty(4);
        }
        System.out.println("-------------------------------------------------------------------");
        System.out.println(currentPlayer.getName() + " has picked up cards and has missed a turn");
        System.out.println(currentPlayer.getName() + "'s cards are : " + currentPlayer.getHand());
        cardsToBePickedUp = false;
    }

    /*public boolean playerMissesTurn (Player player, UnoCard openCard){
        if (hasToPickUpCards(player, openCard)) {
            player
        }else if (openCard.toString().contains("SKIP")) {
            player
        }return false;

     */
    public void reverse() {
        int count = players.size() - 1; // 3
        for (int i = 0; i < count/2; i++) { // we would like to add a count in respect of number of players
            Player temp = players.get(count);
            players.set(count, players.get(i)); // postavlja count na poziciju 0, jer je i = 0;
            players.set(i, temp);
            count--;
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
        if (players.size() == 0) {
            System.out.println("There should be at least one human player");
        }
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
                    p.setSaidUNO(true);
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

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public UnoCard robotPlays(Player robot, UnoCard currentCard) {
        for (UnoCard c : robot.getHand()) {
            if (c.value.equals(currentCard.value) || c.type.equals(currentCard.type)) {
                return c;
            } else {
                for (UnoCard w : robot.getHand()) {
                    if (w.toString().contains("WILD")) {
                        return w;
                    }
                }
            }
        }
        return null;
    }

    public void printHelp() {
        System.out.println("Key in the name of the card you wish to play!");
        System.out.println("DRAW - Take a new card to play");
        System.out.println("POINTS - Score of all players");
    }

    public void drawCard() {
        playedCard = deckPile.pop();
        currentPlayer.getHand().add(playedCard);
        if (isAllowed()){
            updatePlayedCard();
            System.out.println("Your new card " + playedCard + " has been played");
            if (playedCard.toString().contains("WILD")){
                allowWild();
            }
        } else {
            System.out.println("You cannot play the new card!");
            System.out.println("Your new cards are : " + currentPlayer.getHand());
        }
        return;
    }

    public void createPlayers() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 4; i++) {
            System.out.print("Player Name: ");
            String Player = scanner.next();
            if (Player.equals("stop")) {
                System.out.println("Bots will be added to complete the players!");
                break;
            }
            org.campus02.zam.ss2020.players.Player p = new HumanPlayer(Player);
            addPlayer(p);
        }
        completePlayers();
        Collections.shuffle(players);
        System.out.println("This is the order in which players will play :");
        System.out.println(players);
    }

    public static void main(String[] args) {
        UnoGame game = new UnoGame();
        Player p = new HumanPlayer("p");
        game.completePlayers();
        game.dealCards();
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
}
