package org.campus02.zam.ss2020;

public enum Value {
    ZERO,
    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    REVERSE,
    DRAWTWO,
    SKIP;

    public static final Value[] values = Value.values();

    public static Value getValues(int i) {
        return Value.values[i];
    }
}
