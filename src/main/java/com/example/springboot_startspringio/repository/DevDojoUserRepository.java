package com.example.springboot_startspringio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springboot_startspringio.domain.DevDojoUser;

import java.util.Optional;

public interface DevDojoUserRepository extends JpaRepository<DevDojoUser, Long> {
    Optional<DevDojoUser> findByUsername(String username);
}