package com.mobilelab.facilitybooking.controller;

import com.mobilelab.facilitybooking.model.Facility;
import com.mobilelab.facilitybooking.repository.FacilityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}