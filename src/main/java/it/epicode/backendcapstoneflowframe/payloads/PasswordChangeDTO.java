package it.epicode.backendcapstoneflowframe.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordChangeDTO(
        @NotBlank(message = "La vecchia password è obbligatoria")
        String vecchiaPassword,

        @NotBlank(message = "La nuova password è obbligatoria")
        @Size(min = 8, message = "La nuova password deve avere almeno 8 caratteri")
        String nuovaPassword
) {}
