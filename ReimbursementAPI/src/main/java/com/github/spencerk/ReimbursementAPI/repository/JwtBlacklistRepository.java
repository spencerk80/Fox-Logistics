package com.github.spencerk.ReimbursementAPI.repository;

import com.github.spencerk.ReimbursementAPI.entity.BlacklistedJwt;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JwtBlacklistRepository extends MongoRepository<BlacklistedJwt, String> {
    BlacklistedJwt findByJwt(String jwt);
}
