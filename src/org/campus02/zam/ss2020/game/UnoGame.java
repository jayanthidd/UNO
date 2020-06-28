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
 */
public class UnoGame {
    LinkedList<Player> players;// linkedlist
    Stack<UnoCard> discardPile;
    Stack<UnoCard> deckPile;
    String wildColor;
    private boolean cardsToBePickedUp;

    public UnoGame() {
        this.players = new LinkedList<>();
        this.discardPile = new Stack<>();
        this.deckPile = new Deck().deck;
        this.cardsToBePickedUp=false;
    }

    public boolean isCardsToBePickedUp() {
        return cardsToBePickedUp;
    }

    public void setCardsToBePickedUp(boolean cardsToBePickedUp) {
        this.cardsToBePickedUp = cardsToBePickedUp;
    }

    public Stack<UnoCard> getDiscardPile() {
        return discardPile;
    }

    public void addPlayer(Player p) {
        if (players.contains(p)) {
            System.out.println("Player already exists! Try another name!");
        } else {
            players.add(p);
        }
    }
    public void processCard(Player currentPlayer, UnoCard playedCard, UnoCard openCard){
        if(isAllowed(playedCard, openCard, currentPlayer)){
            updatePlayedCard(currentPlayer, playedCard);
            if (playedCard.toString().contains("WILD")){
                allowWild();
            }
        } else {
            System.out.println("You cannot play that card! Penalty!");
            penalty(currentPlayer, 1);
        }
        if (playedCard.toString().equals("WILDFOUR")||playedCard.toString().contains("DRAWTWO")){
            cardsToBePickedUp = true;
        }
    }

    private void updatePlayedCard(Player currentPlayer, UnoCard playedCard) {
        discardPile.push(playedCard);
        currentPlayer.getHand().remove(playedCard);
    }

    private void allowWild() {
        System.out.print("What color would you like to change to? : ");
        Scanner scanner = new Scanner(System.in);
        String color = scanner.next();
        if (Arrays.toString(Type.values()).contains(color)) {//checking if the color entered by the user is valid
            wildColor = color;
        }
        else {
            System.out.println("Invalid Entry!");
            allowWild();
        }
    }

    public boolean isAllowed(UnoCard playedCard, UnoCard openCard, Player player) {
        if (openCard.toString().contains("WILD")){
            return validateWild(playedCard);
        }
        if (playedCard.toString().contains("WILD")) {
            return isWildAllowed(openCard, player);
        } else if (playedCard.value == openCard.value || playedCard.type == openCard.type) {
            return true;
        } else
            return false;

    }

    /**
     * checks if the current player is allowed to play a wild card
     * @param openCard
     * @param player
     * @return
     */
    private boolean isWildAllowed(UnoCard openCard, Player player) {
        if (player.getHand().contains(openCard.value) || player.getHand().contains(openCard.type)) {
            penalty(player, 1);
            System.out.println("You can't play that card. Penalty!");
            return false;
        } else return true;
    }


    /**
     * WHen the open card is a wild card, this method checks to see if the card played by the player is valid.
     * @param playedCard
     * @return
     */
    private boolean validateWild(UnoCard playedCard) {
        if (discardPile.size()==1){
            return true;
        }
        else if(playedCard.type.toString().equals(wildColor)) {
            return true;
        } else {
            return false;
        }
    }

    public void pickUpCards(Player player) {
        if (discardPile.peek().toString().contains("DRAWTWO")) {
            penalty(player, 2);
        } else if (discardPile.peek().toString().contains("WILDFOUR")) {
            penalty(player, 4);
        }
        System.out.println("-------------------------------------------------------------------");
        System.out.println(player.getName() + " has picked up cards and has missed a turn");
        System.out.println(player.getName()+"'s cards are : " + player.getHand());
        cardsToBePickedUp=false;
    }

    /*public boolean playerMissesTurn (Player player, UnoCard openCard){
        if (hasToPickUpCards(player, openCard)) {
            player
        }else if (openCard.toString().contains("SKIP")) {
            player
        }return false;
    }
     */

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

    public void penalty(Player p, int cards) {
        ArrayList<UnoCard> hand =p.getHand();
        for (int i = 0; i < cards; i++) {
            hand.add(deckPile.pop());
        }
        p.setHand(hand);

    }


    public void completePlayers() {

        int size = players.size();
        if (players.size() == 0) {
            System.out.println("There should be at least one human player");
        }
        if (size == 4) {
            System.out.println("There are 4 players");
        } else{
            for (int i = size; i < 4; i++) {
                players.add(new Robot(String.format("Robot%2d", i)));
                System.out.println();
            }
        }
    }

    public HashMap<String, Integer> playerScores() {
        HashMap<String, Integer> scores = new HashMap<>();
        for (Player p : players) {
            scores.put(p.getName(), p.getPoints());
        }
        return scores;
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
     * @param player
     * @return
     */
    public boolean checkUno(Player player) {
        for (Player p : players) {
            if (p.equals(player)) {
                if (p.getHand().size() == 2) {
                    p.setSaidUNO(true);
                    System.out.println(p.getName() + " said UNO");
                    return true;
                }
                else {
                    System.out.println("You can say Uno only when you have 2 cards left!");
                    return false;
                }
            }
        }
        return false;
    }

    public UnoCard robotPlays (Player robot, UnoCard currentCard) {
        for (UnoCard c : robot.getHand()) {
            if (c.value.equals(currentCard.value)|| c.type.equals(currentCard.type)){
                return c;
            }
            else {
                for (UnoCard w : robot.getHand()){
                    if (w.toString().contains("WILD")){
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

    public void drawCard(Player currentPlayer) {
        currentPlayer.getHand().add(deckPile.pop());
    }

    public static void main(String[] args) {
        UnoGame game = new UnoGame();
        Player p = new HumanPlayer("p");
        game.completePlayers();
        game.dealCards();
    }
}
