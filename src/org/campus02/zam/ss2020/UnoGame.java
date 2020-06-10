package org.campus02.zam.ss2020;

import java.util.*;

public class UnoGame {
    LinkedList<Player> players;// linkedlist
    private Deck deck;
    private ArrayList<UnoCard> discardPile;
    private Stack<UnoCard> deckPile;

    public UnoGame() {
        this.players = new LinkedList<>();
        this.discardPile = new ArrayList<>();
        this.deckPile = new Deck().deck;
    }

    public void addPlayer(Player p) {
        if (players.contains(p)) {
            System.out.println("Player already exists! Try another name!");
            return;
        }
        else {
            players.add(p);
        }
    }

    public boolean isAllowed(UnoCard card, UnoCard opencard, Player player) {
        if (card.toString().contains("WILD")){
            if (player.getHand().contains(opencard.value)||player.getHand().contains(opencard.type)){
                penalty(player, 1);
                return false;
            }
            else return true;
        }
        else if (card.value == opencard.value || card.type == opencard.type || card.toString().contains("WILD")){
            return true;
        }
        else {
            penalty(player,1);
            return false;
        }
    }

    public void dealCards(Player player) {

        ArrayList<UnoCard> deal = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            UnoCard card = deck.deck.pop();
            deal.add(card);
        }
        //System.out.println(deck.size());
        player.setHand(deal);
    }

    public void penalty (Player p, int cards) {
        for (int i = 0; i < cards; i++) {
            ArrayList<UnoCard> hand = p.getHand();
            hand.add(deckPile.pop());
            p.setHand(hand);

        }
    }


    public void completePlayers() {
        
        int size = players.size();
        if (players.size() == 0){
            System.out.println("There should be at least one human player");
        }
        if (size == 4) {
            System.out.println("There are 4 players");
        }
        else {
            for (int i = size; i< 4; i++) {
                players.add(new Robot("Robot %d", i));
            }
        }
        System.out.println(Arrays.toString(players.toArray()));
    }

    public HashMap<String, Integer> playerScores() {
        HashMap<String, Integer> scores = new HashMap<>();
        for (Player p : players){
            scores.put(p.getName(), p.getPoints());
        }
        return scores;
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
