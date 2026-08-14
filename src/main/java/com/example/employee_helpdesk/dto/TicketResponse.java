package com.example.employee_helpdesk.dto;

import java.time.LocalDateTime;

import com.example.employee_helpdesk.enums.TicketPriority;

public class TicketResponse {
	 private Long id;
	    private String ticketNumber;
	    private String title;
	    private String description;
	    private TicketPriority priority;
	    public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getTicketNumber() {
			return ticketNumber;
		}
		public void setTicketNumber(String ticketNumber) {
			this.ticketNumber = ticketNumber;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public TicketPriority getPriority() {
			return priority;
		}
		public void setPriority(TicketPriority priority) {
			this.priority = priority;
		}
		public String getCreator() {
			return creator;
		}
		public void setCreator(String creator) {
			this.creator = creator;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}
		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}
		private String creator;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
}
