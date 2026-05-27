package it.epicode.backendcapstoneflowframe.repositories;

import it.epicode.backendcapstoneflowframe.entities.Progetto;
import it.epicode.backendcapstoneflowframe.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProgettoRepository extends JpaRepository<Progetto, UUID> {
    List<Progetto> findByCreatore(Utente creatore);
}
