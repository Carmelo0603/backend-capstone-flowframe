package it.epicode.backendcapstoneflowframe.payloads;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO(
        @NotBlank(message = "Lo username è obbligatorio")
        String username,

        @NotBlank(message = "L'email è obbligatoria")
        @Email(message = "Formato email non valido")
        String email,

        @NotBlank(message = "La password è obbligatoria")
        @Size(min = 6, message = "La password deve contenere almeno 6 caratteri")
        String password
) {}