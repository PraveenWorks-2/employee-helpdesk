package com.manoj.notification.template.service;

import com.manoj.notification.template.dto.TemplateRequestDto;
import com.manoj.notification.template.dto.TemplateResponseDto;
import com.manoj.notification.template.entity.Template;
import com.manoj.notification.template.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;

    @Override
    public TemplateResponseDto createTemplate(TemplateRequestDto requestDto) {
        if (templateRepository.existsByTemplateCode(requestDto.getTemplateCode())) {
            throw new IllegalArgumentException("Template with code '" + requestDto.getTemplateCode() + "' already exists.");
        }

        Template template = Template.builder()
                .templateCode(requestDto.getTemplateCode())
                .templateName(requestDto.getTemplateName())
                .content(requestDto.getContent())
                .notificationType(requestDto.getNotificationType())
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

    private TemplateResponseDto mapToResponseDto(Template entity) {
        return TemplateResponseDto.builder()
                .id(entity.getId())
                .templateCode(entity.getTemplateCode())
                .templateName(entity.getTemplateName())
                .content(entity.getContent())
                .notificationType(entity.getNotificationType())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}