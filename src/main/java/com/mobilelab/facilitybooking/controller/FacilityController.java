package com.mobilelab.facilitybooking.controller;

import com.mobilelab.facilitybooking.model.Facility;
import com.mobilelab.facilitybooking.repository.FacilityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController                    // Tells Spring: this class handles HTTP requests
@RequestMapping("/facilities")     // Base URL: all endpoints start with /facilities
public class FacilityController {

    private final FacilityRepository facilityRepo;

    // Constructor Injection — Spring automatically provides the repository
    public FacilityController(FacilityRepository facilityRepo) {
        this.facilityRepo = facilityRepo;
    }

    // GET /facilities — Retrieve all facilities
    @GetMapping
    public ResponseEntity<List<Facility>> getAllFacilities() {
        List<Facility> facilities = facilityRepo.findAll();
        return ResponseEntity.ok(facilities);
    }

    // GET /facilities/{id} — Retrieve a specific facility
    @GetMapping("/{id}")
    public ResponseEntity<Facility> getFacilityById(@PathVariable Long id) {
        return facilityRepo.findById(id)
                .map(ResponseEntity::ok)                          // found → return 200
                .orElse(ResponseEntity.notFound().build());       // not found → return 404
    }

    // POST /facilities — Create a new facility
    @PostMapping
    public ResponseEntity<?> createFacility(@RequestBody Map<String, Object> body) {
        String name     = (String) body.get("name");
        String location = (String) body.get("location");
        Object capRaw   = body.get("capacity");

        if (name == null || location == null || capRaw == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "name, location, and capacity are required"));
        }

        Integer capacity = Integer.parseInt(capRaw.toString());
        Facility facility = new Facility(name, location, capacity);
        Facility saved = facilityRepo.save(facility);

        return ResponseEntity.status(201).body(saved);
    }
}