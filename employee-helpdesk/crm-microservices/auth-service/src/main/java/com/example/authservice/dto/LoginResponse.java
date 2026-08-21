package com.example.authservice.dto;

public class LoginResponse {

    private String token;
    private String username;
    private String role;

    public LoginResponse() {
    }

    public LoginResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    // Getter
    public String getToken() {
        return token;
    }

    // Setter
    public void setToken(String token) {
        this.token = token;
    }

    // Getter
    public String getUsername() {
        return username;
    }

    // Setter
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter
    public String getRole() {
        return role;
    }

    // Setter
    public void setRole(String role) {
        this.role = role;
    }
}