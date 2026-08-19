package com.manoj.notification.template.dto;

import jakarta.validation.constraints.NotBlank;

public class TemplateRequestDto {

    @NotBlank(message = "Template code is required")
    private String templateCode;

    @NotBlank(message = "Template name is required")
    private String templateName;

    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Notification type is required")
    private String notificationType;

    public TemplateRequestDto() {}

    public TemplateRequestDto(String templateCode, String templateName, String content, String notificationType) {
        this.templateCode = templateCode;
        this.templateName = templateName;
        this.content = content;
        this.notificationType = notificationType;
    }

    public String getTemplateCode() { return templateCode; }
    public void setTemplateCode(String templateCode) { this.templateCode = templateCode; }

    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getNotificationType() { return notificationType; }
    public void setNotificationType(String notificationType) { this.notificationType = notificationType; }

    public static TemplateRequestDtoBuilder builder() {
        return new TemplateRequestDtoBuilder();
    }

    public static class TemplateRequestDtoBuilder {
        private String templateCode;
        private String templateName;
        private String content;
        private String notificationType;

        public TemplateRequestDtoBuilder templateCode(String templateCode) { this.templateCode = templateCode; return this; }
        public TemplateRequestDtoBuilder templateName(String templateName) { this.templateName = templateName; return this; }
        public TemplateRequestDtoBuilder content(String content) { this.content = content; return this; }
        public TemplateRequestDtoBuilder notificationType(String notificationType) { this.notificationType = notificationType; return this; }

        public TemplateRequestDto build() {
            return new TemplateRequestDto(templateCode, templateName, content, notificationType);
        }
    }
}