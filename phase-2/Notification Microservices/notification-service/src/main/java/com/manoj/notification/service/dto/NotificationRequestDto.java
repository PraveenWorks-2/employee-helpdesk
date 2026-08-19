package com.manoj.notification.service.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Map;

public class NotificationRequestDto {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Template ID cannot be null")
    private Long templateId;

    private Map<String, String> placeholders;

    public NotificationRequestDto() {}

    public NotificationRequestDto(Long userId, Long templateId, Map<String, String> placeholders) {
        this.userId = userId;
        this.templateId = templateId;
        this.placeholders = placeholders;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Map<String, String> getPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(Map<String, String> placeholders) {
        this.placeholders = placeholders;
    }

    public static NotificationRequestDtoBuilder builder() {
        return new NotificationRequestDtoBuilder();
    }

    public static class NotificationRequestDtoBuilder {
        private Long userId;
        private Long templateId;
        private Map<String, String> placeholders;

        public NotificationRequestDtoBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public NotificationRequestDtoBuilder templateId(Long templateId) {
            this.templateId = templateId;
            return this;
        }

        public NotificationRequestDtoBuilder placeholders(Map<String, String> placeholders) {
            this.placeholders = placeholders;
            return this;
        }

        public NotificationRequestDto build() {
            return new NotificationRequestDto(userId, templateId, placeholders);
        }
    }
}