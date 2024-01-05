package com.example.MPRprojekt.Exceptions;

public class InvalidCarDataException extends RuntimeException{
    public InvalidCarDataException(String message) {
        super(message);
    }
}
