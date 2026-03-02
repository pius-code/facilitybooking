package com.mobilelab.facilitybooking.controller;

import com.mobilelab.facilitybooking.model.Booking;
import com.mobilelab.facilitybooking.model.Facility;
import com.mobilelab.facilitybooking.model.Users;
import com.mobilelab.facilitybooking.repository.BookingRepository;
import com.mobilelab.facilitybooking.repository.FacilityRepository;
import com.mobilelab.facilitybooking.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingRepository bookingRepo;
    private final FacilityRepository facilityRepo;
    private final UserRepository userRepo;

    public BookingController(BookingRepository bookingRepo,
                             FacilityRepository facilityRepo,
                             UserRepository userRepo) {
        this.bookingRepo = bookingRepo;
        this.facilityRepo = facilityRepo;
        this.userRepo = userRepo;
    }

    // GET /bookings — Retrieve all bookings
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingRepo.findAllWithDetails());
    }

    // POST /bookings — Create a booking
    // Why Map<String, Object>? Because the client sends JSON with user_id and facility_id
    // as numbers, not full User/Facility objects. We need to look them up ourselves.
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Map<String, Object> request) {

        // Step 1: Look up the user and facility from the database
        Long userId = Long.valueOf(request.get("user_id").toString());
        Long facilityId = Long.valueOf(request.get("facility_id").toString());

        Users user = userRepo.findById(userId).orElse(null);
        Facility facility = facilityRepo.findById(facilityId).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }
        if (facility == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Facility not found"));
        }

        // Step 2: Build the Booking object
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setFacility(facility);
        booking.setDate(LocalDate.parse(request.get("date").toString()));
        booking.setStartTime(java.time.LocalTime.parse(request.get("start_time").toString()));
        booking.setEndTime(java.time.LocalTime.parse(request.get("end_time").toString()));
        booking.setStatus("CONFIRMED");

        // Step 3: Save and return
        Booking saved = bookingRepo.save(booking);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT /bookings/{id} — Update a booking
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id,
                                           @RequestBody Map<String, Object> request) {
        return bookingRepo.findById(id)
                .map(existing -> {
                    // Only update fields that are provided
                    if (request.containsKey("date")) {
                        existing.setDate(LocalDate.parse(request.get("date").toString()));
                    }
                    if (request.containsKey("start_time")) {
                        existing.setStartTime(java.time.LocalTime.parse(request.get("start_time").toString()));
                    }
                    if (request.containsKey("end_time")) {
                        existing.setEndTime(java.time.LocalTime.parse(request.get("end_time").toString()));
                    }
                    if (request.containsKey("status")) {
                        existing.setStatus(request.get("status").toString());
                    }
                    return ResponseEntity.ok(bookingRepo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /bookings/{id} — Cancel a booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        if (!bookingRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookingRepo.deleteById(id);
        return ResponseEntity.noContent().build();   // 204 — deleted successfully
    }
}