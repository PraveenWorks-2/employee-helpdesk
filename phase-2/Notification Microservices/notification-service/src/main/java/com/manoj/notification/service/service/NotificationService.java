package com.manoj.notification.service.service;

import com.manoj.notification.service.dto.NotificationRequestDto;
import com.manoj.notification.service.dto.NotificationResponseDto;
import java.util.List;

public interface NotificationService {
    NotificationResponseDto sendNotification(NotificationRequestDto requestDto);
    List<NotificationResponseDto> getUserNotifications(Long userId);
    NotificationResponseDto markAsRead(Long notificationId);
}