package it.epicode.backendcapstoneflowframe.payloads;

import jakarta.validation.constraints.NotEmpty;

public record ProgettoPayloadDTO(
        @NotEmpty(message = "Il titolo del progetto è obbligatorio.")
        String titolo
) {}
