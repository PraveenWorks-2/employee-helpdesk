package com.example.api_gateway.security;

import java.time.Instant;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String KEY_ID = "purchase-gateway-key";

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(
            String username,
            String role) {

        Instant now = Instant.now();

        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                        .subject(username)
                        .claim("role", role)
                        .issuedAt(now)
                        .expiresAt(now.plusSeconds(3600))
                        .build();

        JwsHeader header =
                JwsHeader.with(MacAlgorithm.HS256)
                        .keyId(KEY_ID)
                        .build();

        JwtEncoderParameters parameters =
                JwtEncoderParameters.from(
                        header,
                        claims
                );

        return jwtEncoder
                .encode(parameters)
                .getTokenValue();
    }
}