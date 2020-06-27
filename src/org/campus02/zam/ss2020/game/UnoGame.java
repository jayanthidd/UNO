package org.campus02.zam.ss2020.game;

import org.campus02.zam.ss2020.cards.Deck;
import org.campus02.zam.ss2020.cards.Type;
import org.campus02.zam.ss2020.cards.UnoCard;
import org.campus02.zam.ss2020.players.HumanPlayer;
import org.campus02.zam.ss2020.players.Player;
import org.campus02.zam.ss2020.players.Robot;

import java.util.*;

public class UnoGame {
    LinkedList<Player> players;// linkedlist
    Deck deck;
    LinkedList<UnoCard> discardPile;
    Stack<UnoCard> deckPile;
    String wildColor;

    public UnoGame() {
        this.players = new LinkedList<>();
        this.discardPile = new LinkedList<>();
        this.deckPile = new Deck().deck;
    }

    public LinkedList<UnoCard> getDiscardPile() {
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
        if (currentPlayer.getName().contains("Robot")){

        }
        else {
            if(isAllowed(playedCard, openCard, currentPlayer)){
                if (playedCard.equals("WILD")||playedCard.equals("WILD_FOUR")){
                    allowWild();
                }
            }
        }
    }

    private void allowWild() {
        System.out.print("What color would you like to change to? : ");
        Scanner scanner = new Scanner(System.in);
        if (Type.values().toString().contains(scanner.next())) {
            wildColor = scanner.next();
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
            if (player.getHand().contains(openCard.value) || player.getHand().contains(openCard.type)) {
                penalty(player, 1);
                System.out.println("You can't play that card. Penalty!");
                return false;
            } else return true;
        } else if (playedCard.value == openCard.value || playedCard.type == openCard.type || playedCard.toString().contains("WILD")) {
            return true;
        } else {
            System.out.println("You can't play that card. Penalty!");
            penalty(player, 1);
            return false;
        }
    }

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

    public boolean pickUpCards(Player player, UnoCard openCard) {
        if (openCard.toString().contains("DRAWTWO") || !player.saysUNO()) {
            penalty(player, 2);
            return true;

        } else if (openCard.toString().contains("WILDFOUR")) {
            penalty(player, 4);
            return true;
        }
        return false;
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
            //System.out.println(deck.size());
            System.out.println(deal);
            p.setHand(deal);
        }
    }

    public void penalty(Player p, int cards) {
        for (int i = 0; i < cards; i++) {
            ArrayList<UnoCard> hand = p.getHand();
            hand.add(deckPile.pop());
            p.setHand(hand);
        }
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
        System.out.println(Arrays.toString(players.toArray()));
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


    public void setSaidUNO(Player player) {
        for (Player p : players) {
            if (p.equals(player)) {
                if (p.getHand().size() == 2) {
                    p.saysUNO();
                    System.out.println(p.getName() + " said UNO");
                }
            }
        }
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

    public static void main(String[] args) {
        Player jay = new HumanPlayer("Jayanthi");
        Deck d = new Deck();
        d.shuffle();
        jay.setHand(d.dealCards());
        System.out.println(jay.getHand());
        jay.setPoints(jay.getHand());
        System.out.println(jay.getPoints());

        Player Emina = new HumanPlayer("Emina");
        Emina.setHand(d.dealCards());
        System.out.println(Emina.getHand());
        Emina.setPoints(Emina.getHand());
        System.out.println(Emina.getPoints());

        Player iro = new HumanPlayer("Iro");
        iro.setHand(d.dealCards());
        System.out.println(iro.getHand());
        iro.setPoints(iro.getHand());
        System.out.println(iro.getPoints());

        Player elisabeth = new HumanPlayer("Elisabeth");
        elisabeth.setHand(d.dealCards());
        System.out.println(elisabeth.getHand());
        elisabeth.setPoints(elisabeth.getHand());
        System.out.println(elisabeth.getPoints());

        UnoGame pm = new UnoGame();
        pm.addPlayer(jay);
        pm.addPlayer(Emina);
        pm.addPlayer(iro);
        pm.addPlayer(elisabeth);

        System.out.println(d.deck.size());

        System.out.println(pm.playerScores());

    }




}