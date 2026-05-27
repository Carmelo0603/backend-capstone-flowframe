package it.epicode.backendcapstoneflowframe.services;


import it.epicode.backendcapstoneflowframe.entities.Ruolo;
import it.epicode.backendcapstoneflowframe.entities.Utente;
import it.epicode.backendcapstoneflowframe.exceptions.BadRequestException;
import it.epicode.backendcapstoneflowframe.exceptions.NotFoundException;
import it.epicode.backendcapstoneflowframe.payloads.UserRegisterDTO;
import it.epicode.backendcapstoneflowframe.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    public Utente findById(UUID id) {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato."));
    }

    // Creazione utente con hash della password tramite BCrypt
    public Utente save(UserRegisterDTO dto) {
        if (utenteRepository.findByEmail(dto.email()).isPresent()) {
            throw new BadRequestException("L'email " + dto.email() + " è già in uso.");
        }
        if (utenteRepository.findByUsername(dto.username()).isPresent()) {
            throw new BadRequestException("Lo username " + dto.username() + " è già in uso.");
        }

        Utente u = new Utente();
        u.setUsername(dto.username());
        u.setEmail(dto.email());
        u.setPassword(bcrypt.encode(dto.password()));
        // Assegnazione ruolo di default per le nuove registrazioni
        u.setRuolo(Ruolo.UTENTE_NORMALE);

        return utenteRepository.save(u);
    }
}
