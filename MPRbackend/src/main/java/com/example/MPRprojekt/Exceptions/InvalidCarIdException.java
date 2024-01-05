package com.example.MPRprojekt.Exceptions;

public class InvalidCarIdException extends RuntimeException{
    public InvalidCarIdException(String message) {
        super(message);
    }
}
