package com.mobilelab.facilitybooking.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(nullable = false, length = 20)
    private String status;

    public Booking() {
        // default constructor for JPA
    }

    public Booking(Facility facility, Users user, LocalDate date,
                   LocalTime startTime, LocalTime endTime, String status) {
        this.facility = facility;
        this.user = user;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    // --- id ---
    public Long getId() {
        return this.id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    // --- facility ---
    public Facility getFacility() {
        return this.facility;
    }

    public void setFacility(Facility newFacility) {
        this.facility = newFacility;
    }

    // --- user ---
    public Users getUser() {
        return this.user;
    }

    public void setUser(Users newUser) {
        this.user = newUser;
    }

    // --- date ---
    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate newDate) {
        this.date = newDate;
    }

    // --- startTime ---
    public LocalTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalTime newStartTime) {
        this.startTime = newStartTime;
    }

    // --- endTime ---
    public LocalTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalTime newEndTime) {
        this.endTime = newEndTime;
    }

    // --- status ---
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    @Override
    public String toString() {
        return "Booking{id=" + id + ", date=" + date + ", startTime=" + startTime +
               ", endTime=" + endTime + ", status='" + status + "'}";
    }
}