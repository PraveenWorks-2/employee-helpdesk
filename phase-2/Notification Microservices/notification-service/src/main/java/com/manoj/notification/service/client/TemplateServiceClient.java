package com.manoj.notification.service.client;

import com.manoj.notification.service.dto.TemplateClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "template-service", path = "/api/templates")
public interface TemplateServiceClient {

    @GetMapping("/{id}")
    TemplateClientDto getTemplateById(@PathVariable("id") Long id);
}