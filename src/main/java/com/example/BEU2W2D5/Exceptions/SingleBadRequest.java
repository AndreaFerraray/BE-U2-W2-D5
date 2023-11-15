package com.example.BEU2W2D5.Exceptions;

public class SingleBadRequest extends RuntimeException{
    public SingleBadRequest(String message) {
        super(message);
    }
}