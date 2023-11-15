package com.example.BEU2W2D5.entities.Payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DispositivoPayload(
        @NotEmpty(message = "Il nome è obbligatorio")
        @Size(message = "Il nome deve avere almeno 3 caratteri",min = 3)
        String tipo
) {
}
