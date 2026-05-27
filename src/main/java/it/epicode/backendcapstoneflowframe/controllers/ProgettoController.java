package it.epicode.backendcapstoneflowframe.controllers;

import it.epicode.backendcapstoneflowframe.entities.Progetto;
import it.epicode.backendcapstoneflowframe.entities.Utente;
import it.epicode.backendcapstoneflowframe.payloads.ProgettoPayloadDTO;
import it.epicode.backendcapstoneflowframe.services.ProgettoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/progetti")
public class ProgettoController {

    @Autowired
    private ProgettoService progettoService;

    @GetMapping("/me")
    public List<Progetto> getProgetti(@AuthenticationPrincipal Utente currentUser) {
        return progettoService.findByUtente(currentUser);
    }

    @GetMapping("/{id}")
    public Progetto getProgettoById(@PathVariable UUID id) {
        return progettoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Progetto createProgetto(@RequestBody @Validated ProgettoPayloadDTO body, @AuthenticationPrincipal Utente currentUser) {
        return progettoService.save(body, currentUser);
    }

    @PutMapping("/{id}/blueprint")
    public Progetto updateBlueprint(@PathVariable UUID id, @RequestBody String nuovoBlueprint, @AuthenticationPrincipal Utente currentUser) {
        return progettoService.updateBlueprint(id, nuovoBlueprint, currentUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProgetto(@PathVariable UUID id, @AuthenticationPrincipal Utente currentUser) {
        progettoService.findByIdAndDelete(id, currentUser);
    }
}
