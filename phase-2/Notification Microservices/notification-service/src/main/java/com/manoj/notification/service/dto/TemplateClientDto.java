package com.manoj.notification.service.dto;

public class TemplateClientDto {

    private Long id;
    private String templateCode;
    private String templateName;
    private String content;
    private String notificationType;

    public TemplateClientDto() {}

    public TemplateClientDto(Long id, String templateCode, String templateName, String content, String notificationType) {
        this.id = id;
        this.templateCode = templateCode;
        this.templateName = templateName;
        this.content = content;
        this.notificationType = notificationType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public static TemplateClientDtoBuilder builder() {
        return new TemplateClientDtoBuilder();
    }

    public static class TemplateClientDtoBuilder {
        private Long id;
        private String templateCode;
        private String templateName;
        private String content;
        private String notificationType;

        public TemplateClientDtoBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TemplateClientDtoBuilder templateCode(String templateCode) {
            this.templateCode = templateCode;
            return this;
        }

        public TemplateClientDtoBuilder templateName(String templateName) {
            this.templateName = templateName;
            return this;
        }

        public TemplateClientDtoBuilder content(String content) {
            this.content = content;
            return this;
        }

        public TemplateClientDtoBuilder notificationType(String notificationType) {
            this.notificationType = notificationType;
            return this;
        }

        public TemplateClientDto build() {
            return new TemplateClientDto(id, templateCode, templateName, content, notificationType);
        }
    }
}