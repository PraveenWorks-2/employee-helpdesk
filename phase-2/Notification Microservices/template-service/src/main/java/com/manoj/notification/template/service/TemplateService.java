package com.manoj.notification.template.service;

import com.manoj.notification.template.dto.TemplateRequestDto;
import com.manoj.notification.template.dto.TemplateResponseDto;

public interface TemplateService {
    TemplateResponseDto createTemplate(TemplateRequestDto requestDto);
    TemplateResponseDto getTemplateById(Long id);
}