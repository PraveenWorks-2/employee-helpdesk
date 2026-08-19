package com.example.api_gateway.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    // ---------------------------------------------------------
    // Password Encoder
    // ---------------------------------------------------------

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

    // ---------------------------------------------------------
    // JWT Authentication Converter
    // ---------------------------------------------------------

    @Bean
    public Converter<Jwt, Mono<JwtAuthenticationToken>>
            jwtAuthenticationConverter() {

        return jwt -> {

            String role =
                    jwt.getClaimAsString("role");

            List<SimpleGrantedAuthority> authorities;

            if (role == null || role.isBlank()) {

                authorities = List.of();

            } else {

                authorities = List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + role
                        )
                );
            }

            JwtAuthenticationToken authentication =
                    new JwtAuthenticationToken(
                            jwt,
                            authorities
                    );

            return Mono.just(authentication);
        };
    }

    // ---------------------------------------------------------
    // Security Filter Chain
    // ---------------------------------------------------------

    @Bean
    public SecurityWebFilterChain securityFilterChain(
            ServerHttpSecurity http,
            Converter<Jwt, Mono<JwtAuthenticationToken>>
                    jwtAuthenticationConverter) {

        return http

                // Disable CSRF for REST APIs
                .csrf(
                        ServerHttpSecurity.CsrfSpec::disable
                )

                // Disable browser login
                .formLogin(
                        ServerHttpSecurity.FormLoginSpec::disable
                )

                // Disable HTTP Basic
                .httpBasic(
                        ServerHttpSecurity.HttpBasicSpec::disable
                )

                // Authorization rules
                .authorizeExchange(exchange -> exchange

                        // Login is public
                        .pathMatchers(
                                "/auth/login"
                        ).permitAll()

                        // Actuator
                        .pathMatchers(
                                "/actuator/**"
                        ).permitAll()

                        // Swagger
                        .pathMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Purchase APIs
                        .pathMatchers(
                                "/api/purchases/**"
                        ).hasAnyRole(
                                "EMPLOYEE",
                                "MANAGER"
                        )

                        // Approval APIs
                        .pathMatchers(
                                "/api/approvals/**"
                        ).hasRole(
                                "MANAGER"
                        )

                        // Everything else requires authentication
                        .anyExchange()
                        .authenticated()
                )

                // JWT Resource Server
                .oauth2ResourceServer(
                        oauth2 -> oauth2
                                .jwt(
                                        jwt -> jwt
                                                .jwtAuthenticationConverter(
                                                        jwtAuthenticationConverter
                                                )
                                )
                )

                .build();
    }
}