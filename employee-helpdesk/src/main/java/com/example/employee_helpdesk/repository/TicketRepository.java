package com.example.employee_helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee_helpdesk.entity.TicketEntity;
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findByCreator(String creator);

}