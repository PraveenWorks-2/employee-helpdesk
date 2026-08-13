package com.example.employee_helpdesk.service;

import com.example.employee_helpdesk.dto.request.LoginRequest;
import com.example.employee_helpdesk.dto.request.RegisterRequest;
import com.example.employee_helpdesk.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
    
}