package com.manoj.notification.service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemplateClientDto {
    private Long id;
    private String templateCode;
    private String templateName;
    private String content;
    private String notificationType;
}