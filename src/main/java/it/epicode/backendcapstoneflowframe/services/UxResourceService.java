package it.epicode.backendcapstoneflowframe.services;

import it.epicode.backendcapstoneflowframe.entities.UxResource;
import it.epicode.backendcapstoneflowframe.repositories.UxResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UxResourceService {

    private final UxResourceRepository repository;

    public UxResourceService(UxResourceRepository repository) {
        this.repository = repository;
    }

    public List<UxResource> getAll() {
        return repository.findAll();
    }

    public List<UxResource> getByCategory(String category) {
        return repository.findByCategory(category);
    }
}