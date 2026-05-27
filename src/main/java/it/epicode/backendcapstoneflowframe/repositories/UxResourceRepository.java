package it.epicode.backendcapstoneflowframe.repositories;

import it.epicode.backendcapstoneflowframe.entities.UxResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UxResourceRepository extends JpaRepository<UxResource, Long> {
    List<UxResource> findByCategory(String category);
}
