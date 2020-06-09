package org.campus02.zam.ss2020;

public enum Value {
    ZERO {public int getPoints(){return 0;}},
    ONE {public int getPoints(){return 1;}},
    TWO{public int getPoints(){return 2;}},
    THREE {public int getPoints(){return 3;}},
    FOUR {public int getPoints(){return 4;}},
    FIVE {public int getPoints(){return 5;}},
    SIX {public int getPoints(){return 6;}},
    SEVEN {public int getPoints(){return 7;}},
    EIGHT {public int getPoints(){return 8;}},
    NINE {public int getPoints(){return 9;}},
    REVERSE {public int getPoints(){return 20;}},
    DRAWTWO {public int getPoints(){return 20;}},
    SKIP {public int getPoints(){return 20;}},
    WILD {public int getPoints(){return 50;}},
    WILDFOUR {public int getPoints(){return 50;}};

    public static final Value[] values = Value.values();

    public static Value getValues(int i) {
        return Value.values[i];
    }
}
