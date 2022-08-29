package com.github.spencerk.ReimbursementAPI.repository;

import com.github.spencerk.ReimbursementAPI.entity.ReimbursementTicket;
import com.github.spencerk.ReimbursementAPI.enums.ReimbursementStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReimbursementTicketRepository extends MongoRepository<ReimbursementTicket, UUID> {
    Page<ReimbursementTicket> findAllByStatus(ReimbursementStatus status, Pageable pageable);
    Page<ReimbursementTicket> findAllByEmployeeID(UUID employeeID, Pageable pageable);
    Page<ReimbursementTicket> findByAllByEmployeeIDAndStatus(UUID employeeID, ReimbursementStatus status, Pageable page);
}
