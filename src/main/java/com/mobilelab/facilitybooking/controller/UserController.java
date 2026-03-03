package com.mobilelab.facilitybooking.controller;

import com.mobilelab.facilitybooking.model.Users;
import com.mobilelab.facilitybooking.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepo;

    public UserController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // GET /users — Retrieve all users
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userRepo.findAll());
    }

    // GET /users/{id} — Retrieve a specific user
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        return userRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /users — Create a new user
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Map<String, Object> body) {
        String name  = (String) body.get("name");
        String email = (String) body.get("email");
        String role  = (String) body.get("role");

        if (name == null || email == null || role == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "name, email, and role are required"));
        }

        Users user = new Users(name, email, role);
        Users saved = userRepo.save(user);

        return ResponseEntity.status(201).body(saved);
    }
}
