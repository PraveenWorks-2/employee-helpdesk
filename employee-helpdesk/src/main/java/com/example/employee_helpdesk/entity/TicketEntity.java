package com.example.employee_helpdesk.entity;

import java.time.LocalDateTime;

import com.example.employee_helpdesk.enums.TicketPriority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="Tickets")
public class TicketEntity {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
private long id;

@Column(unique=true, nullable=false)
private String ticketnumber;

@Column(nullable=false)
private String title;

@Column(nullable=false, length=2000)
private String description;

@Enumerated(EnumType.STRING)
private TicketPriority priority;

private String creator;

private LocalDateTime createdAt;
private LocalDateTime updatedAt;

@PostPersist
public void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getTicketnumber() {
	return ticketnumber;
}

public void setTicketnumber(String ticketnumber) {
	this.ticketnumber = ticketnumber;
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

@PostUpdate
public void onUpdate() {
    updatedAt = LocalDateTime.now();
}
}
