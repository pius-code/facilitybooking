package com.mobilelab.facilitybooking.repository;

import com.mobilelab.facilitybooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByFacilityIdAndDate(Long facilityId, LocalDate date);

    @Query("SELECT b FROM Booking b JOIN FETCH b.facility JOIN FETCH b.user")
    List<Booking> findAllWithDetails();
}