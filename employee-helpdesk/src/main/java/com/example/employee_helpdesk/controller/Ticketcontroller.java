package com.example.employee_helpdesk.controller;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee_helpdesk.dto.TicketRequest;
import com.example.employee_helpdesk.entity.TicketEntity;
import com.example.employee_helpdesk.service.TicketService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
public class Ticketcontroller {
	private final TicketService ticketService;
	 public Ticketcontroller(TicketService ticketService) {
	        this.ticketService = ticketService;
	    }
	// Create ticket
	 
	    @PostMapping
	    public TicketEntity createTicket(
	            @Valid @RequestBody TicketRequest request) {

	        return ticketService.createTicket(request);
	    }
	    
	    // Employee view tickets
	    @GetMapping
	    public List<TicketEntity> getMyTickets(
	            @RequestParam String creator) {

	        return ticketService.getMyTickets(creator);
	    }
	    

	    // Get single ticket
	    @GetMapping("/{id}")
	    public TicketEntity getTicket(@PathVariable Long id) {
	        return ticketService.getTicketById(id);
	    }

}
