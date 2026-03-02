package com.mobilelab.facilitybooking.controller;

import com.mobilelab.facilitybooking.model.Booking;
import com.mobilelab.facilitybooking.repository.BookingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/availability")
public class AvailabilityController {

    private final BookingRepository bookingRepo;

    public AvailabilityController(BookingRepository bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    // GET /availability?facility_id=1&date=2026-02-20
    // Returns all bookings for that facility on that date
    // If empty → facility is fully available that day
    @GetMapping
    public ResponseEntity<?> checkAvailability(
            @RequestParam Long facility_id,
            @RequestParam String date) {

        LocalDate bookingDate = LocalDate.parse(date);
        List<Booking> existingBookings = bookingRepo.findByFacilityIdAndDate(facility_id, bookingDate);

        if (existingBookings.isEmpty()) {
            return ResponseEntity.ok(Map.of(
                "facility_id", facility_id,
                "date", date,
                "available", true,
                "message", "No bookings found. Facility is fully available."
            ));
        }

        return ResponseEntity.ok(Map.of(
            "facility_id", facility_id,
            "date", date,
            "available", false,
            "existing_bookings", existingBookings
        ));
    }
}