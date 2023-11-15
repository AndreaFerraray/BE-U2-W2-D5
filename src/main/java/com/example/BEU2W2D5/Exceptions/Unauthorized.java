package com.example.BEU2W2D5.Exceptions;

import lombok.Getter;

@Getter

public class Unauthorized extends RuntimeException{
    public Unauthorized(String message) {
        super(message);
    }
}

