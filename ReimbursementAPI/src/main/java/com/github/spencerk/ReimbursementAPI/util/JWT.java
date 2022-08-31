package com.github.spencerk.ReimbursementAPI.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JWT {
    private final Logger logger = LoggerFactory.getLogger(JWT.class);
    private final String secretKey;

    public JWT() {
        this.secretKey = System.getenv("JWT_KEY");
    }
    public JWT(String testKey) { this.secretKey = testKey; }

    public String createJWT(UserDetails userDetails) {
        LocalDateTime   iat         = LocalDateTime.now();
        LocalDateTime   exp         = iat.plusDays(1);
        byte[]          keyBytes    = DatatypeConverter.parseBase64Binary(secretKey);
        Key             signingKey  = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
        JwtBuilder      jwtBuilder  = Jwts.builder()
                                          .setIssuedAt(Date.from(iat.atZone(ZoneId.systemDefault()).toInstant()))
                                          .setExpiration(Date.from(exp.atZone(ZoneId.systemDefault()).toInstant()))
                                          .claim("username", userDetails.getUsername())
                                          .claim("role", userDetails.getAuthorities().toString())
                                          .signWith(signingKey);

        logger.trace("JWT.createJWT(userDetails)");
        logger.info("JWT created");

        return jwtBuilder.compact();
    }

    public String getUsername(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        logger.trace("JWT.getUsername(jwt)");
        logger.info("Username extracted from JWT");

        return (String) claims.get("username");
    }

    public String getUserRole(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        logger.trace("JWT.getUserRole(jwt)");
        logger.info("User role extracted from JWT");

        return (String) claims.get("role");
    }

    public LocalDateTime getJwtExpiration(String jwt) {
        logger.trace("JWT.getJwtExpiration(jwt)");
        logger.info("Expiration timestamp extracted from JWT");

        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getExpiration()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public boolean validateJwt(String jwt, UserDetails userDetails) {
        Claims claims;

        logger.trace("JWT.validateJwt(jwt, userDetails)");

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch(Exception e) {
            logger.error(String.format("Could not validate JWT: %s", e.getMessage()));

            return false;
        }

        if( ! (claims.get("username")).equals(userDetails.getUsername())) {
            logger.info("JWT not valid! Username does not match");

            return false;
        }
        if( ! (claims.get("role")).equals(userDetails.getAuthorities().toString())) {
            logger.info("JWT not valid! Role does not match");

            return false;
        }

        if(claims.getExpiration().before(new Date())) {
            logger.info("JWT not valid! Token is expired");

            return false;
        }
        logger.info("JWT is valid");

        return true;
    }
}