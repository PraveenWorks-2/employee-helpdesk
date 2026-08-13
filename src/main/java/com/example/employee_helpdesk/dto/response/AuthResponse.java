package com.example.employee_helpdesk.dto.response;

import com.example.employee_helpdesk.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String token;

    private String username;

    private String email;

    private Role role;
    
}