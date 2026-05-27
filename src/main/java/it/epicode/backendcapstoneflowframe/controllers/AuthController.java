package it.epicode.backendcapstoneflowframe.controllers;

import it.epicode.backendcapstoneflowframe.entities.Utente;
import it.epicode.backendcapstoneflowframe.payloads.LoginResponseDTO;
import it.epicode.backendcapstoneflowframe.payloads.UserLoginDTO;
import it.epicode.backendcapstoneflowframe.payloads.UserRegisterDTO;
import it.epicode.backendcapstoneflowframe.services.AuthService;
import it.epicode.backendcapstoneflowframe.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody @Validated UserLoginDTO body) {
        String token = authService.authenticateUser(body);
        return new LoginResponseDTO(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente register(@RequestBody @Validated UserRegisterDTO body) {
        return utenteService.save(body);
    }
}