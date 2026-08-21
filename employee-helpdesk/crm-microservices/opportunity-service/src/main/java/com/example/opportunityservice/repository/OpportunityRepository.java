package com.example.opportunityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.opportunityservice.entity.Opportunity;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {

}