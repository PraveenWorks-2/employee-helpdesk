package com.manoj.notification.template.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "templates")
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "template_code", nullable = false, unique = true, length = 100)
    private String templateCode;

    @Column(name = "template_name", nullable = false, length = 150)
    private String templateName;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "notification_type", nullable = false, length = 30)
    private String notificationType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Template() {}

    public Template(Long id, String templateCode, String templateName, String content, String notificationType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.templateCode = templateCode;
        this.templateName = templateName;
        this.content = content;
        this.notificationType = notificationType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTemplateCode() { return templateCode; }
    public void setTemplateCode(String templateCode) { this.templateCode = templateCode; }

    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static TemplateBuilder builder() {
        return new TemplateBuilder();
    }

    public static class TemplateBuilder {
        private Long id;
        private String templateCode;
        private String templateName;
        private String content;
        private String notificationType;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public TemplateBuilder id(Long id) { this.id = id; return this; }
        public TemplateBuilder templateCode(String templateCode) { this.templateCode = templateCode; return this; }
        public TemplateBuilder templateName(String templateName) { this.templateName = templateName; return this; }
        public TemplateBuilder content(String content) { this.content = content; return this; }
        public TemplateBuilder notificationType(String notificationType) { this.notificationType = notificationType; return this; }
        public TemplateBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public TemplateBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Template build() {
            return new Template(id, templateCode, templateName, content, notificationType, createdAt, updatedAt);
        }
    }
}