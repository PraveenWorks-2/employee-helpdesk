package com.example.employee_helpdesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Allows POST/PUT/DELETE from Swagger
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // Permits all requests for testing
            );
        return http.build();
    }
}