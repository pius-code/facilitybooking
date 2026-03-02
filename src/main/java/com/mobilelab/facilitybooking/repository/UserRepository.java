package com.mobilelab.facilitybooking.repository;

import com.mobilelab.facilitybooking.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}