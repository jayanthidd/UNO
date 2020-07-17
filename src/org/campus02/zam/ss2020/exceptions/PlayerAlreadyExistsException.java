package org.campus02.zam.ss2020.exceptions;

public class PlayerAlreadyExistsException extends Exception {
    public PlayerAlreadyExistsException() {
        System.out.println("This player already exists.  Try another name");
    }
}
