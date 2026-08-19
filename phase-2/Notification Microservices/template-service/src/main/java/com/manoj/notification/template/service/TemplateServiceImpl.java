package com.manoj.notification.template.service;

import com.manoj.notification.template.dto.TemplateRequestDto;
import com.manoj.notification.template.dto.TemplateResponseDto;
import com.manoj.notification.template.entity.Template;
import com.manoj.notification.template.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Autowired
    public TemplateServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public TemplateResponseDto createTemplate(TemplateRequestDto requestDto) {
        if (templateRepository.existsByTemplateCode(requestDto.getTemplateCode())) {
            throw new IllegalArgumentException("Template code already exists: " + requestDto.getTemplateCode());
        }

        Template template = Template.builder()
                .templateCode(requestDto.getTemplateCode())
                .templateName(requestDto.getTemplateName())
                .content(requestDto.getContent())
                .notificationType(requestDto.getNotificationType())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Template savedTemplate = templateRepository.save(template);
        return mapToResponseDto(savedTemplate);
    }

    @Override
    public TemplateResponseDto getTemplateById(Long id) {
        Template template = templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found with ID: " + id));
        return mapToResponseDto(template);
    }

    private TemplateResponseDto mapToResponseDto(Template template) {
        return TemplateResponseDto.builder()
                .id(template.getId())
                .templateCode(template.getTemplateCode())
                .templateName(template.getTemplateName())
                .content(template.getContent())
                .notificationType(template.getNotificationType())
                .createdAt(template.getCreatedAt())
                .updatedAt(template.getUpdatedAt())
                .build();
    }
}