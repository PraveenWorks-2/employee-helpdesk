package com.example.employee_helpdesk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.employee_helpdesk.dto.TicketRequest;
import com.example.employee_helpdesk.entity.TicketEntity;
import com.example.employee_helpdesk.repository.TicketRepository;

@Service
public class TicketService {
	
	 private final TicketRepository ticketRepository;

	    public TicketService(TicketRepository ticketRepository) {
	        this.ticketRepository = ticketRepository;
	    }

	    // Employee creates ticket
	    public TicketEntity createTicket(TicketRequest request) {
	    	
	        TicketEntity ticket = new TicketEntity();

	        ticket.setTicketnumber("TKT-" +
	                System.currentTimeMillis());

	        ticket.setTitle(request.getTitle());
	        ticket.setDescription(request.getDescription());
	        ticket.setPriority(request.getPriority());
	        ticket.setCreator(request.getCreator());
	        return ticketRepository.save(ticket);
	    }
	    
	 // Employee views own tickets
	    public List<TicketEntity> getMyTickets(String creator) {
	        return ticketRepository.findByCreator(creator);
	    }
	    
	 // Get ticket by ID
	    public TicketEntity getTicketById(Long id) {

	        return ticketRepository.findById(id)
	                .orElseThrow(() ->
	                        new RuntimeException("Ticket not found"));
	    }

}
