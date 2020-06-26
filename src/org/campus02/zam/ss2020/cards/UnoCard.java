package org.campus02.zam.ss2020.cards;

public class UnoCard {

    public Type type;
    public Value value;
    private String card;
    private int cardPoints;

    public UnoCard(Type type, Value value) {

        this.type = type;
        this.value = value;
        card = type.name() +"_" + value.name();
        cardPoints = value.points; // need to check if this works
    }

    public int getCardPoints() {
        return cardPoints;
    }

    public UnoCard(Value value) {
        this.value = value;
        this.card = value.name();
    }

    public Type getType() {
        return type;
    }

    public Value getValue() {
        return value;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public String getCard() {
        return card.toString();
    }

    @Override
    public String toString() {
        return card;
    }
}
