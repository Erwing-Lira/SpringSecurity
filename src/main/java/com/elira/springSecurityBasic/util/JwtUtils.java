package com.elira.springSecurityBasic.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${spring.security.jwt.key.private}")
    private String privateKey;

    @Value("${spring.security.jwt.user.generator}")
    private String userGenerator;

    public String createToken(
            Authentication authentication
    ) {
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
                // 2 minutes
                .withExpiresAt(new Date(System.currentTimeMillis() + 120000))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);
        return jwtToken;
    }

    public DecodedJWT validateToken(
            String token
    ) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(this.userGenerator)
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            return decodedJWT;
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token invalid, not Authorized");
        }
    }

    public String extractUsername(
            DecodedJWT decodedJWT
    ) {
        return decodedJWT.getSubject();
    }

    public Claim getSpecificClaim(
            DecodedJWT decodedJWT,
            String claimName
    ) {
        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> getAllClaims(
            DecodedJWT decodedJWT
    ) {
        return decodedJWT.getClaims();
    }
}
