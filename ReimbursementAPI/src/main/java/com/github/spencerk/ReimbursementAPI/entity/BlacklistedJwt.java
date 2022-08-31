package com.github.spencerk.ReimbursementAPI.entity;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class BlacklistedJwt {
    @Id
    private String id;
    private String jwt;
    LocalDateTime expiresAt;

    public BlacklistedJwt() {}

    public BlacklistedJwt(String jwt, LocalDateTime expiresAt) {
        this.jwt = jwt;
        this.expiresAt = expiresAt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
