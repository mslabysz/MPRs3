package com.example.MPRprojekt.Exceptions;

public class NoCarsFoundException extends RuntimeException {
    public NoCarsFoundException(String message) {
        super(message);
    }
}
