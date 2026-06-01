package it.epicode.backendcapstoneflowframe.services;

import it.epicode.backendcapstoneflowframe.entities.Utente;
import it.epicode.backendcapstoneflowframe.exceptions.UnauthorizedException;
import it.epicode.backendcapstoneflowframe.payloads.UserLoginDTO;
import it.epicode.backendcapstoneflowframe.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateUser(UserLoginDTO dto) {
        Utente u = utenteService.findByEmail(dto.email());
        if (!u.getIsVerified()) {
            throw new UnauthorizedException("Account non attivato. Controlla la tua casella email e clicca sul link di verifica.");
        }


        if (passwordEncoder.matches(dto.password(), u.getPassword())) {
            return jwtTools.createToken(u);
        } else {
            throw new UnauthorizedException("Credenziali non valide.");
        }
    }
}