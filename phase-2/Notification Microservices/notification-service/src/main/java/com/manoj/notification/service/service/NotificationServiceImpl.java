package com.manoj.notification.service.service;

import com.manoj.notification.service.client.TemplateServiceClient;
import com.manoj.notification.service.dto.NotificationRequestDto;
import com.manoj.notification.service.dto.NotificationResponseDto;
import com.manoj.notification.service.dto.TemplateClientDto;
import com.manoj.notification.service.entity.Notification;
import com.manoj.notification.service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final TemplateServiceClient templateServiceClient;

    @Override
    public NotificationResponseDto sendNotification(NotificationRequestDto requestDto) {
        TemplateClientDto template = templateServiceClient.getTemplateById(requestDto.getTemplateId());
        if (template == null) {
            throw new RuntimeException("Failed to fetch template with ID: " + requestDto.getTemplateId());
        }

        String renderedMessage = renderTemplate(template.getContent(), requestDto.getPlaceholders());

        Notification notification = Notification.builder()
                .userId(requestDto.getUserId())
                .templateId(template.getId())
                .renderedMessage(renderedMessage)
                .notificationType(template.getNotificationType())
                .isRead(false)
                .build();

        Notification saved = notificationRepository.save(notification);
        return mapToDto(saved);
    }

    @Override
    public List<NotificationResponseDto> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public NotificationResponseDto markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notificationId));

        notification.setRead(true);
        notification.setReadAt(LocalDateTime.now());

        Notification updated = notificationRepository.save(notification);
        return mapToDto(updated);
    }

    private String renderTemplate(String templateContent, Map<String, String> placeholders) {
        if (placeholders == null || placeholders.isEmpty()) {
            return templateContent;
        }
        String result = templateContent;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            result = result.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        return result;
    }

    private NotificationResponseDto mapToDto(Notification entity) {
        return NotificationResponseDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .templateId(entity.getTemplateId())
                .renderedMessage(entity.getRenderedMessage())
                .notificationType(entity.getNotificationType())
                .isRead(entity.isRead())
                .createdAt(entity.getCreatedAt())
                .readAt(entity.getReadAt())
                .build();
    }
}