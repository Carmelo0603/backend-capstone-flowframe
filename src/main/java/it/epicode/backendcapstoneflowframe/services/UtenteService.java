package it.epicode.backendcapstoneflowframe.services;

import it.epicode.backendcapstoneflowframe.entities.Ruolo;
import it.epicode.backendcapstoneflowframe.entities.Utente;
import it.epicode.backendcapstoneflowframe.exceptions.BadRequestException;
import it.epicode.backendcapstoneflowframe.exceptions.NotFoundException;
import it.epicode.backendcapstoneflowframe.payloads.PasswordChangeDTO;
import it.epicode.backendcapstoneflowframe.payloads.UserRegisterDTO;
import it.epicode.backendcapstoneflowframe.payloads.UserUpdateDTO;
import it.epicode.backendcapstoneflowframe.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtenteService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public Utente findById(UUID id) {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato."));
    }


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

        u.setRuolo(Ruolo.UTENTE_NORMALE);


        String codiceVerifica = UUID.randomUUID().toString();
        u.setVerificationCode(codiceVerifica);


        Utente utenteSalvato = utenteRepository.save(u);


        emailService.sendVerificationEmail(utenteSalvato.getEmail(), utenteSalvato.getUsername(), codiceVerifica);

        return utenteSalvato;
    }

    public Utente updateProfile(UUID id, UserUpdateDTO body) {
        Utente u = this.findById(id);

        if (!u.getEmail().equals(body.email()) && utenteRepository.findByEmail(body.email()).isPresent()) {
            throw new BadRequestException("L'email " + body.email() + " è già in uso.");
        }

        if (!u.getUsername().equals(body.username()) && utenteRepository.findByUsername(body.username()).isPresent()) {
            throw new BadRequestException("Lo username " + body.username() + " è già in uso.");
        }

        u.setUsername(body.username());
        u.setEmail(body.email());

        return utenteRepository.save(u);
    }

    public Utente changePassword(UUID id, PasswordChangeDTO body) {
        Utente u = this.findById(id);

        if (!passwordEncoder.matches(body.vecchiaPassword(), u.getPassword())) {
            throw new BadRequestException("La vecchia password è errata.");
        }

        if (passwordEncoder.matches(body.nuovaPassword(), u.getPassword())) {
            throw new BadRequestException("La nuova password non può essere uguale alla precedente.");
        }

        u.setPassword(passwordEncoder.encode(body.nuovaPassword()));
        return utenteRepository.save(u);
    }

    public boolean verifyUser(String verificationCode) {

        Utente u = utenteRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new BadRequestException("Codice di verifica non valido o scaduto."));

        u.setIsVerified(true);
        u.setVerificationCode(null);
        utenteRepository.save(u);
        return true;
    }
}