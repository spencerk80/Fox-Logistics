package com.github.spencerk.ReimbursementAPI.repository;

import com.github.spencerk.ReimbursementAPI.Entity.ReimbursementTicket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ReimbursementTicketRepository extends MongoRepository<ReimbursementTicket, UUID> {
}
