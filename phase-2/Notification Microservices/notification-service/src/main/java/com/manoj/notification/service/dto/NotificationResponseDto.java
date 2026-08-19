package com.manoj.notification.service.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponseDto {
    private Long id;
    private Long userId;
    private Long templateId;
    private String renderedMessage;
    private String notificationType;
    private boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
}