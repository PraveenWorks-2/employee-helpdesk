package com.example.leadservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.leadservice.dto.LeadRequest;
import com.example.leadservice.dto.LeadResponse;
import com.example.leadservice.entity.Lead;
import com.example.leadservice.repository.LeadRepository;
import com.example.leadservice.service.LeadServiceImpl;

@ExtendWith(MockitoExtension.class)
class LeadServiceTest {

    @Mock
    private LeadRepository leadRepository;

    @InjectMocks
    private LeadServiceImpl leadService;

    @Test
    void createLead_ShouldCreateLead() {

        // Arrange
        LeadRequest request = new LeadRequest();

        request.setFirstName("Sneha");
        request.setLastName("B");
        request.setEmail("sneha@test.com");
        request.setPhone("9876543210");
        request.setCompany("CRM Company");

        Lead savedLead = new Lead();

        savedLead.setId(1L);
        savedLead.setFirstName("Sneha");
        savedLead.setLastName("B");
        savedLead.setEmail("sneha@test.com");
        savedLead.setPhone("9876543210");
        savedLead.setCompany("CRM Company");
        savedLead.setStatus("NEW");

        when(leadRepository.save(any(Lead.class)))
                .thenReturn(savedLead);

        // Act
        LeadResponse response =
                leadService.createLead(request);

        // Assert
        assertNotNull(response);

        assertEquals(1L, response.getId());
        assertEquals("Sneha", response.getFirstName());
        assertEquals("B", response.getLastName());
        assertEquals("sneha@test.com", response.getEmail());
        assertEquals("9876543210", response.getPhone());
        assertEquals("CRM Company", response.getCompany());
        assertEquals("NEW", response.getStatus());
    }
}