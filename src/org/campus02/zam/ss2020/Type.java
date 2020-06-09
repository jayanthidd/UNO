package org.campus02.zam.ss2020;

public enum Type {
    RED,
    GREEN,
    BLUE,
    YELLOW;


    private  static final Type[] types = Type.values();

    public static Type getTypes(int i) {
        return Type.types[i];
    }
}



