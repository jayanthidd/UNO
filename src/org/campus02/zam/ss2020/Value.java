package org.campus02.zam.ss2020;

public enum Value {
    ZERO (0),
    ONE (1),
    TWO (2),
    THREE (3),
    FOUR (4),
    FIVE (5),
    SIX (6),
    SEVEN (7),
    EIGHT (8),
    NINE (9),
    REVERSE (20),
    DRAWTWO (20),
    SKIP (20),
    WILD (50),
    WILDFOUR (50);
    int points;
    Value (int points){
        this.points = points;
    }

    public static final Value[] values = Value.values();

    public static Value getValues(int i) {
        return Value.values[i];
    }
}
