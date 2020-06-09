package org.campus02.zam.ss2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class PlayerManagement {
    LinkedList<Player> players;// linkedlist

    public PlayerManagement() {
        this.players = new LinkedList<>();
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
    }

    public HashMap<String, Integer> playerScores() {
        HashMap<String, Integer> scores = new HashMap<>();
        for (Player p : players){
            scores.put(p.name, p.points);
        }
        return scores;
    }

    public void setPoints (Player p) {
        p.points = 15; //just a change

    }
}
