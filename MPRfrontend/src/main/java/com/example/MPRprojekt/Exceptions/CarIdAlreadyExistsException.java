package com.example.MPRprojekt.Exceptions;

public class CarIdAlreadyExistsException extends RuntimeException{
    public CarIdAlreadyExistsException(String message) {
        super(message);
    }
}
