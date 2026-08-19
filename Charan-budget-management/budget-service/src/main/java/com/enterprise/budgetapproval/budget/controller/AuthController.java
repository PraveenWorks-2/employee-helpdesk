package com.enterprise.budgetapproval.budget.controller;

import com.enterprise.budgetapproval.budget.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        String role = (request.getRole() != null && !request.getRole().isBlank())
                ? request.getRole()
                : "FINANCE_ADMIN";
        List<String> roles = List.of(role);
        String token = jwtUtil.generateToken(request.getUsername(), roles);
        return Map.of("token", token);
    }

    public static class LoginRequest {
        private String username;
        private String password;
        private String role;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}