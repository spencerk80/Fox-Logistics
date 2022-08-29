package com.github.spencerk.ReimbursementAPI.repository;

import com.github.spencerk.ReimbursementAPI.Entity.ReimbursementTicket;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReimbursementTicketRepository extends MongoRepository<ReimbursementTicket, UUID> {
}
