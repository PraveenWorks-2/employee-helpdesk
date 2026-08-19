package com.example.api_gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.api_gateway.dto.LoginRequest;
import com.example.api_gateway.dto.LoginResponse;
import com.example.api_gateway.security.JwtService;
import com.example.api_gateway.security.UserService;
import com.example.api_gateway.security.UserService.UserData;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UserService userService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request) {

        UserData user =
                userService.findUser(
                        request.getUsername()
                );

        if (user == null) {

            return ResponseEntity
                    .status(
                            HttpStatus.UNAUTHORIZED
                    )
                    .body(
                            "Invalid username or password"
                    );
        }

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {

            return ResponseEntity
                    .status(
                            HttpStatus.UNAUTHORIZED
                    )
                    .body(
                            "Invalid username or password"
                    );
        }

        String token =
                jwtService.generateToken(
                        request.getUsername(),
                        user.getRole()
                );

        LoginResponse response =
                new LoginResponse(
                        token,
                        request.getUsername(),
                        user.getRole()
                );

        return ResponseEntity.ok(response);
    }
}