package com.example.BEU2W2D5.Exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;
@Getter
public class BadRequest extends RuntimeException {
    List<ObjectError> errorsList;
    public BadRequest(String message) {
        super(message);
    }
    public BadRequest(List<ObjectError> errorsList)
    {
        super("Errore:");
        this.errorsList=errorsList;
    }
}