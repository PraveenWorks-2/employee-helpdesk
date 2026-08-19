package com.manoj.notification.template.dto;

import java.time.LocalDateTime;

public class TemplateResponseDto {

    private Long id;
    private String templateCode;
    private String templateName;
    private String content;
    private String notificationType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TemplateResponseDto() {}

    public TemplateResponseDto(Long id, String templateCode, String templateName, String content, String notificationType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.templateCode = templateCode;
        this.templateName = templateName;
        this.content = content;
        this.notificationType = notificationType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public static TemplateResponseDtoBuilder builder() {
        return new TemplateResponseDtoBuilder();
    }

    public static class TemplateResponseDtoBuilder {
        private Long id;
        private String templateCode;
        private String templateName;
        private String content;
        private String notificationType;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public TemplateResponseDtoBuilder id(Long id) { this.id = id; return this; }
        public TemplateResponseDtoBuilder templateCode(String templateCode) { this.templateCode = templateCode; return this; }
        public TemplateResponseDtoBuilder templateName(String templateName) { this.templateName = templateName; return this; }
        public TemplateResponseDtoBuilder content(String content) { this.content = content; return this; }
        public TemplateResponseDtoBuilder notificationType(String notificationType) { this.notificationType = notificationType; return this; }
        public TemplateResponseDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public TemplateResponseDtoBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public TemplateResponseDto build() {
            return new TemplateResponseDto(id, templateCode, templateName, content, notificationType, createdAt, updatedAt);
        }
    }
}