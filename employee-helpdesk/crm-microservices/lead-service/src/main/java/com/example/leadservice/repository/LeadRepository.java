package com.example.leadservice.repository;

import com.example.leadservice.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LeadRepository extends JpaRepository<Lead, Long> {

    Optional<Lead> findByEmail(String email);

    boolean existsByEmail(String email);
}