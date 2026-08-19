package com.manoj.notification.template.controller;

import com.manoj.notification.template.dto.TemplateRequestDto;
import com.manoj.notification.template.dto.TemplateResponseDto;
import com.manoj.notification.template.service.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping
    public ResponseEntity<TemplateResponseDto> createTemplate(@Valid @RequestBody TemplateRequestDto requestDto) {
        TemplateResponseDto createdTemplate = templateService.createTemplate(requestDto);
        return new ResponseEntity<>(createdTemplate, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateResponseDto> getTemplateById(@PathVariable Long id) {
        TemplateResponseDto template = templateService.getTemplateById(id);
        return ResponseEntity.ok(template);
    }
}