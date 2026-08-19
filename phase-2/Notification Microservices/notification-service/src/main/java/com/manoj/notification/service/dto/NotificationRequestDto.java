package com.manoj.notification.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Template ID is required")
    private Long templateId;

    private Map<String, String> placeholders;
}