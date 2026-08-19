package com.manoj.notification.service.service;

import com.manoj.notification.service.client.TemplateServiceClient;
import com.manoj.notification.service.dto.NotificationRequestDto;
import com.manoj.notification.service.dto.NotificationResponseDto;
import com.manoj.notification.service.dto.TemplateClientDto;
import com.manoj.notification.service.entity.Notification;
import com.manoj.notification.service.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private TemplateServiceClient templateServiceClient;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Test
    void testSendNotification_Success() {
        TemplateClientDto templateDto = TemplateClientDto.builder()
                .id(1L)
                .templateCode("TEST_CODE")
                .content("Hello {{name}}, code is {{code}}")
                .notificationType("SMS")
                .build();

        Notification saved = Notification.builder()
                .id(10L)
                .userId(101L)
                .templateId(1L)
                .renderedMessage("Hello Manoj, code is 4321")
                .notificationType("SMS")
                .isRead(false)
                .build();

        when(templateServiceClient.getTemplateById(1L)).thenReturn(templateDto);
        when(notificationRepository.save(any(Notification.class))).thenReturn(saved);

        NotificationRequestDto request = NotificationRequestDto.builder()
                .userId(101L)
                .templateId(1L)
                .placeholders(Map.of("name", "Manoj", "code", "4321"))
                .build();

        NotificationResponseDto result = notificationService.sendNotification(request);

        assertNotNull(result);
        assertEquals("Hello Manoj, code is 4321", result.getRenderedMessage());
        assertFalse(result.isRead());
    }

    @Test
    void testMarkAsRead_Success() {
        Notification notification = Notification.builder()
                .id(1L)
                .userId(101L)
                .templateId(1L)
                .renderedMessage("Sample")
                .notificationType("EMAIL")
                .isRead(false)
                .build();

        when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));
        when(notificationRepository.save(any(Notification.class))).thenAnswer(i -> i.getArgument(0));

        NotificationResponseDto result = notificationService.markAsRead(1L);

        assertTrue(result.isRead());
        assertNotNull(result.getReadAt());
    }
}