package com.github.spencerk.ReimbursementAPI.service;

import com.github.spencerk.ReimbursementAPI.entity.BlacklistedJwt;
import com.github.spencerk.ReimbursementAPI.util.JWT;
import com.github.spencerk.ReimbursementAPI.repository.JwtBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtBlacklistService {

    private JwtBlacklistRepository jwtBlacklistDao;
    private JWT             JWT;

    @Autowired
    public JwtBlacklistService(JwtBlacklistRepository repo, JWT jwt) {
        jwtBlacklistDao = repo;
        this.JWT = jwt;
    }

    public void blacklistJwt(String jwt) {
        BlacklistedJwt blacklistedJwt = new BlacklistedJwt(
                jwt,
                JWT.getJwtExpiration(jwt)
        );

        jwtBlacklistDao.save(blacklistedJwt);
    }

    public boolean jwtIsOnBlacklist(String jwt) {
        BlacklistedJwt blacklistedJwt = jwtBlacklistDao.findByJwt(jwt);

        return blacklistedJwt != null;
    }
}
