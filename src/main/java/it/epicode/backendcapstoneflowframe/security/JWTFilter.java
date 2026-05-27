package it.epicode.backendcapstoneflowframe.security;

import it.epicode.backendcapstoneflowframe.entities.Utente;
import it.epicode.backendcapstoneflowframe.exceptions.UnauthorizedException;
import it.epicode.backendcapstoneflowframe.services.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private UtenteService utenteService;

    // Intercettazione delle richieste HTTP per validare il token Authorization
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Header Authorization mancante o token malformato.");
        }

        String token = authHeader.substring(7);
        jwtTools.verifyToken(token);

        String id = jwtTools.extractIdFromToken(token);
        Utente currentUser = utenteService.findById(UUID.fromString(id));

        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    // Esclusione del filtro per gli endpoint pubblici
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        AntPathMatcher matcher = new AntPathMatcher();

        return matcher.match("/auth/**", path) || matcher.match("/api/ux-resources/**", path);
    }
}
