package it.epicode.backendcapstoneflowframe.controllers;

import it.epicode.backendcapstoneflowframe.entities.Utente;
import it.epicode.backendcapstoneflowframe.payloads.PasswordChangeDTO;
import it.epicode.backendcapstoneflowframe.payloads.UserUpdateDTO;
import it.epicode.backendcapstoneflowframe.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;


    @GetMapping("/me")
    public Utente getCurrentUser(@AuthenticationPrincipal Utente currentUser) {
        return currentUser;
    }


    @PutMapping("/me")
    public Utente updateCurrentUser(@AuthenticationPrincipal Utente currentUser, @RequestBody @Validated UserUpdateDTO body) {
        return utenteService.updateProfile(currentUser.getId(), body);
    }

    @PutMapping("/me/password")
    public Utente updateCurrentPassword(@AuthenticationPrincipal Utente currentUser, @RequestBody @Validated PasswordChangeDTO body) {
        return utenteService.changePassword(currentUser.getId(), body);
    }
}
