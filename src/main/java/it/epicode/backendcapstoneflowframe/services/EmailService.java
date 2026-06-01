package it.epicode.backendcapstoneflowframe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendWelcomeEmail(String to, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("System Access: Benvenuto in FLOWFRAME");
        message.setText("Ciao " + username + ",\n\n" +
                "Il tuo account è stato creato con successo. Benvenuto nell'ambiente di progettazione strutturale FLOWFRAME.\n\n" +
                "Ricorda: Logic First, Pixel Second.\n\n" +
                "Il Team di Sviluppo");

        mailSender.send(message);
        System.out.println("Email di benvenuto inviata con successo a " + to);
    }

    public void sendVerificationEmail(String to, String username, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Azione Richiesta: Verifica la tua email per FLOWFRAME");


        String verificationUrl = "http://localhost:5173/verify?code=" + verificationCode;

        message.setText("Ciao " + username + ",\n\n" +
                "Per completare la registrazione e accedere a FLOWFRAME, devi confermare questo indirizzo email cliccando sul link sottostante:\n\n" +
                verificationUrl + "\n\n" +
                "Se non hai richiesto tu questa iscrizione, ignora questa mail.\n\n" +
                "Il Team di Sviluppo");

        mailSender.send(message);
        System.out.println("Email di verifica inviata a " + to);
    }
}
