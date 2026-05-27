package it.epicode.backendcapstoneflowframe.services;

import it.epicode.backendcapstoneflowframe.entities.Progetto;
import it.epicode.backendcapstoneflowframe.entities.Utente;
import it.epicode.backendcapstoneflowframe.exceptions.NotFoundException;
import it.epicode.backendcapstoneflowframe.payloads.ProgettoPayloadDTO;
import it.epicode.backendcapstoneflowframe.repositories.ProgettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProgettoService {

    @Autowired
    private ProgettoRepository progettoRepository;

    public List<Progetto> findByUtente(Utente utente) {
        return progettoRepository.findByCreatore(utente);
    }

    public Progetto findById(UUID id) {
        return progettoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Progetto con ID " + id + " non trovato."));
    }

    public Progetto save(ProgettoPayloadDTO body, Utente utente) {
        Progetto nuovo = new Progetto();
        nuovo.setTitolo(body.titolo());
        nuovo.setCreatore(utente);
        nuovo.setBlueprint("[]");
        return progettoRepository.save(nuovo);
    }

    public Progetto updateBlueprint(UUID id, String nuovoBlueprint, Utente utente) {
        Progetto progetto = this.findById(id);
        if (!progetto.getCreatore().getId().equals(utente.getId())) {
            throw new RuntimeException("Non sei autorizzato a modificare questo progetto.");
        }
        progetto.setBlueprint(nuovoBlueprint);
        return progettoRepository.save(progetto);
    }

    public void findByIdAndDelete(UUID id, Utente utente) {
        Progetto progetto = this.findById(id);
        if (!progetto.getCreatore().getId().equals(utente.getId())) {
            throw new RuntimeException("Non sei autorizzato a eliminare questo progetto.");
        }
        progettoRepository.delete(progetto);
    }
}
