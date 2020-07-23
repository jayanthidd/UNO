package org.campus02.zam.ss2020.players;

import org.campus02.zam.ss2020.cards.Type;
import org.campus02.zam.ss2020.cards.UnoCard;
import org.campus02.zam.ss2020.cards.Value;

public class Robot extends Player {

    public Robot(String name) {
        super(name);
    }

    @Override
    public String playCard(UnoCard openCard, String wildColor) {
        System.out.println("Your cards are : " + getHand());
        System.out.print("What card would you like to play? :");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String robotInput;
        if (getHand().size()==2 && !isUNOstatus()) {
            System.out.println("UNO");
            return "UNO";
        }

        if (openCard.toString().contains("WILD")){// if the open card is wild and it is the first card of the round
            if (null==wildColor){
                for(UnoCard c : getHand()){
                    if(!c.toString().contains("WILD")){
                        robotInput = c.toString();
                        System.out.println(c.toString());
                        return c.toString();
                    }
                }
            }
            for (UnoCard c : getHand()){
                if (c.toString().contains(wildColor)){
                    robotInput = c.toString();
                    System.out.println(c.toString());
                    return c.toString();
                }
            }
        }
        for (UnoCard c : getHand()) {
            if (c.value.equals(openCard.value) || c.type.equals(openCard.type)) {
                robotInput = c.toString();
                System.out.println(c.toString());
                return c.toString();
            }
        }
            for (UnoCard w : getHand()) {
                if (w.toString().contains("WILD")) {
                    System.out.println(w.toString());
                    return w.toString();
                }
            }
        System.out.println("DRAW");
        return "DRAW";
    }

    @Override
    public String playWild() {
        if (getHand().size()<=1){
            System.out.println("GREEN"); // randomise the color
            return "GREEN";
        }
        for (UnoCard c : getHand()) {
            if (!c.type.toString().contains("WILD")) {
                System.out.println(c.type.toString());
                return c.type.toString();
            }
        }
        return null;
    }
}
