package com.mobilelab.facilitybooking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 30)
    private String role;

    protected Users() {
        // default constructor for JPA
    }

    public Users(String userName, String userEmail, String userRole) {
        this.name = userName;
        this.email = userEmail;
        this.role = userRole;
    }

    // --- id ---
    public Long getId() {
        return this.id;
    }

    public void setId(Long newId) {
        this.id = newId;
    }

    // --- name ---
    public String getName() {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    // --- email ---
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    // --- role ---
    public String getRole() {
        return this.role;
    }

    public void setRole(String newRole) {
        this.role = newRole;
    }

    @Override
    public String toString() {
        return "Users{id=" + id + ", name='" + name + "', email='" + email + "', role='" + role + "'}";
    }
}