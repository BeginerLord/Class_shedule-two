package com.unicar.Class_shedule.commons.security.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

     @Value("${security.jwt.key.private}")
    private String privateKey;

    // Inyecta el nombre del generador de usuario (issuer) del token JWT desde el archivo de configuración
    @Value("${security.jwt.user.generator}")
    private String userGenerator;


    public String createToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String username = authentication.getPrincipal().toString();
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String jwtToken = JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
        return jwtToken;
    }



    // Método para validar un token JWT y retornar el token decodificado si es válido
    public DecodedJWT validateToken(String token) {
        try {
            // Crea un algoritmo HMAC256 con la clave privada para verificar el token
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            // Configura un verificador JWT con el issuer esperado
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();

            // Verifica y retorna el token JWT decodificado
            return jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            // Si la verificación falla, lanza una excepción indicando que el token no es válido
            throw new JWTVerificationException("Token no válido", e);
        }
    }

    // Método para extraer el nombre de usuario (subject) de un token JWT decodificado
    public String extractUsername(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    // Método para obtener un claim específico de un token JWT decodificado
    public Claim getSpecificClaim(DecodedJWT decodedJWT, String claimName) {
        return decodedJWT.getClaim(claimName);
    }
}