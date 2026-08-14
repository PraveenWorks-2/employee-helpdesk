package com.example.employee_helpdesk.dto;

import com.example.employee_helpdesk.enums.TicketPriority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TicketRequest {
	@NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private TicketPriority priority;

    @NotBlank
    private String creator;

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

}
