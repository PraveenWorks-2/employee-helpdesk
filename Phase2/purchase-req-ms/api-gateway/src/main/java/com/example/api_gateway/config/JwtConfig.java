package com.example.api_gateway.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

@Configuration
public class JwtConfig {

    private static final String KEY_ID = "purchase-gateway-key";

    @Bean
    public SecretKey jwtSecretKey(
            @Value("${jwt.secret}") String secret) {

        return new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256"
        );
    }

    /*
     * Creates JWT tokens
     */
    @Bean
    public JwtEncoder jwtEncoder(
            SecretKey secretKey) {

        OctetSequenceKey jwk =
                new OctetSequenceKey.Builder(secretKey)
                        .algorithm(JWSAlgorithm.HS256)
                        .keyID(KEY_ID)
                        .build();

        JWKSet jwkSet = new JWKSet(jwk);

        return new NimbusJwtEncoder(
                new ImmutableJWKSet<>(jwkSet)
        );
    }

    /*
     * Validates JWT tokens coming into the Gateway
     */
    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder(
            SecretKey secretKey) {

        return NimbusReactiveJwtDecoder
                .withSecretKey(secretKey)
                .build();
    }
}