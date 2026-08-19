package com.manoj.notification.service.dto;

import java.time.LocalDateTime;

public class NotificationResponseDto {

    private Long id;
    private Long userId;
    private Long templateId;
    private String renderedMessage;
    private String notificationType;
    private boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;

    public NotificationResponseDto() {}

    public NotificationResponseDto(Long id, Long userId, Long templateId, String renderedMessage, String notificationType, boolean isRead, LocalDateTime createdAt, LocalDateTime readAt) {
        this.id = id;
        this.userId = userId;
        this.templateId = templateId;
        this.renderedMessage = renderedMessage;
        this.notificationType = notificationType;
        this.isRead = isRead;
        this.createdAt = createdAt;
        this.readAt = readAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getTemplateId() { return templateId; }
    public void setTemplateId(Long templateId) { this.templateId = templateId; }

    public String getRenderedMessage() { return renderedMessage; }
    public void setRenderedMessage(String renderedMessage) { this.renderedMessage = renderedMessage; }

    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getReadAt() { return readAt; }
    public void setReadAt(LocalDateTime readAt) { this.readAt = readAt; }

    public static NotificationResponseDtoBuilder builder() {
        return new NotificationResponseDtoBuilder();
    }

    public static class NotificationResponseDtoBuilder {
        private Long id;
        private Long userId;
        private Long templateId;
        private String renderedMessage;
        private String notificationType;
        private boolean isRead;
        private LocalDateTime createdAt;
        private LocalDateTime readAt;

        public NotificationResponseDtoBuilder id(Long id) { this.id = id; return this; }
        public NotificationResponseDtoBuilder userId(Long userId) { this.userId = userId; return this; }
        public NotificationResponseDtoBuilder templateId(Long templateId) { this.templateId = templateId; return this; }
        public NotificationResponseDtoBuilder renderedMessage(String renderedMessage) { this.renderedMessage = renderedMessage; return this; }
        public NotificationResponseDtoBuilder notificationType(String notificationType) { this.notificationType = notificationType; return this; }
        public NotificationResponseDtoBuilder isRead(boolean isRead) { this.isRead = isRead; return this; }
        public NotificationResponseDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public NotificationResponseDtoBuilder readAt(LocalDateTime readAt) { this.readAt = readAt; return this; }

        public NotificationResponseDto build() {
            return new NotificationResponseDto(id, userId, templateId, renderedMessage, notificationType, isRead, createdAt, readAt);
        }
    }
}