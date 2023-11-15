package com.example.BEU2W2D5.entities.Payload;

import jakarta.validation.constraints.NotNull;

public record SetDispositivoPayload(
        String stato,
        @NotNull(message = "collega un utente")
        int user_id
) {
}
