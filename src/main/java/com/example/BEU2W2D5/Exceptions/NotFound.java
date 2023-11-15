package com.example.BEU2W2D5.Exceptions;

import lombok.Getter;

@Getter
public class NotFound extends  RuntimeException{
    public  NotFound(String message) {
        super(message);
    }
}