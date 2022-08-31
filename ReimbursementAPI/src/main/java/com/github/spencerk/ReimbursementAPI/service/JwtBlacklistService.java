package com.github.spencerk.ReimbursementAPI.service;

import com.github.spencerk.ReimbursementAPI.entity.BlacklistedJwt;
import com.github.spencerk.ReimbursementAPI.util.JWT;
import com.github.spencerk.ReimbursementAPI.repository.JwtBlacklistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtBlacklistService {
    private final Logger                 logger;
    private final JwtBlacklistRepository jwtBlacklistRepo;
    private final JWT             JWT;

    @Autowired
    public JwtBlacklistService(JwtBlacklistRepository repo, JWT jwt) {
        this.jwtBlacklistRepo = repo;
        this.JWT = jwt;
        this.logger = LoggerFactory.getLogger(JwtBlacklistService.class);
    }

    public void blacklistJwt(String jwt) {
        BlacklistedJwt blacklistedJwt = new BlacklistedJwt(
                jwt,
                JWT.getJwtExpiration(jwt)
        );

        logger.trace("JwtBlacklistService.blacklistJwt(jwt)");

        jwtBlacklistRepo.save(blacklistedJwt);
    }

    public boolean jwtIsOnBlacklist(String jwt) {
        BlacklistedJwt blacklistedJwt = jwtBlacklistRepo.findByJwt(jwt);

        logger.trace("JwtBlacklistService.jwtIsOnBlacklist(jwt)");

        return blacklistedJwt != null;
    }
}
