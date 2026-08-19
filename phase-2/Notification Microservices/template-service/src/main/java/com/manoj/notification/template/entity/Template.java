package com.manoj.notification.template.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String templateCode; // e.g., "ORDER_CONFIRMATION", "WELCOME_EMAIL"

    @Column(nullable = false, length = 150)
    private String templateName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // e.g., "Hello {{name}}, your order #{{orderId}} is confirmed!"

    @Column(nullable = false, length = 30)
    private String notificationType; // e.g., "EMAIL", "SMS", "IN_APP"

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}