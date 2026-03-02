package com.mobilelab.facilitybooking.repository;
import com.mobilelab.facilitybooking.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    
}
