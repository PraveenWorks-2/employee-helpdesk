package com.manoj.notification.template.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateRequestDto {

    @NotBlank(message = "Template code is required")
    @Size(max = 100, message = "Template code must be under 100 characters")
    private String templateCode;

    @NotBlank(message = "Template name is required")
    private String templateName;

    @NotBlank(message = "Template content cannot be blank")
    private String content;

    @NotBlank(message = "Notification type is required (e.g., EMAIL, SMS, IN_APP)")
    private String notificationType;
}