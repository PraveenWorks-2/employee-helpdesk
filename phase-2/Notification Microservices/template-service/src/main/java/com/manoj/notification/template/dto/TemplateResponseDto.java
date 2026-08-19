package com.manoj.notification.template.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateResponseDto {
    private Long id;
    private String templateCode;
    private String templateName;
    private String content;
    private String notificationType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}