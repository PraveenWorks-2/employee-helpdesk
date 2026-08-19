package com.manoj.notification.template.service;

import com.manoj.notification.template.dto.TemplateRequestDto;
import com.manoj.notification.template.dto.TemplateResponseDto;
import com.manoj.notification.template.entity.Template;
import com.manoj.notification.template.repository.TemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TemplateServiceTest {

    @Mock
    private TemplateRepository templateRepository;

    @InjectMocks
    private TemplateServiceImpl templateService;

    private Template template;

    @BeforeEach
    void setUp() {
        template = Template.builder()
                .id(1L)
                .templateCode("TEST_CODE")
                .templateName("Test Template")
                .content("Hello {{name}}")
                .notificationType("EMAIL")
                .build();
    }

    @Test
    void testCreateTemplate_Success() {
        TemplateRequestDto requestDto = TemplateRequestDto.builder()
                .templateCode("TEST_CODE")
                .templateName("Test Template")
                .content("Hello {{name}}")
                .notificationType("EMAIL")
                .build();

        when(templateRepository.existsByTemplateCode("TEST_CODE")).thenReturn(false);
        when(templateRepository.save(any(Template.class))).thenReturn(template);

        TemplateResponseDto response = templateService.createTemplate(requestDto);

        assertNotNull(response);
        assertEquals("TEST_CODE", response.getTemplateCode());
        verify(templateRepository, times(1)).save(any(Template.class));
    }

    @Test
    void testGetTemplateById_Success() {
        when(templateRepository.findById(1L)).thenReturn(Optional.of(template));

        TemplateResponseDto response = templateService.getTemplateById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("TEST_CODE", response.getTemplateCode());
    }
}