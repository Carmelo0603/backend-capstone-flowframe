package it.epicode.backendcapstoneflowframe.controllers;

import it.epicode.backendcapstoneflowframe.entities.UxResource;
import it.epicode.backendcapstoneflowframe.services.UxResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ux-resources")
public class UxResourceController {

    @Autowired
    private UxResourceService uxResourceService;

    @GetMapping
    public ResponseEntity<List<UxResource>> getAllResources(@RequestParam(required = false) String category) {
        if (category != null && !category.isEmpty() && !category.equalsIgnoreCase("All")) {
            return ResponseEntity.ok(uxResourceService.getByCategory(category));
        }
        return ResponseEntity.ok(uxResourceService.getAll());
    }
}