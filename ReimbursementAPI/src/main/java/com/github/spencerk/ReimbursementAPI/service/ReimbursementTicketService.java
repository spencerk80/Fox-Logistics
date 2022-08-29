package com.github.spencerk.ReimbursementAPI.service;

import com.github.spencerk.ReimbursementAPI.entity.ReimbursementTicket;
import com.github.spencerk.ReimbursementAPI.enums.ReimbursementStatus;
import com.github.spencerk.ReimbursementAPI.repository.ReimbursementTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
public class ReimbursementTicketService {
    private ReimbursementTicketRepository repo;

    @Autowired
    public ReimbursementTicketService(ReimbursementTicketRepository repo) {
        this.repo = repo;
    }

    public void saveTicket(ReimbursementTicket ticket) {
        this.repo.save(ticket);
    }

    public Map<String, Object> getAllTickets(Pageable page) {
        return mkPageContents(repo.findAll(page));
    }

    public Map<String, Object> getAllByStatus(ReimbursementStatus status, Pageable page) {
        return mkPageContents(repo.findAllByStatus(status, page));
    }

    public Map<String, Object> getAllByEmployeeID(UUID employeeID, Pageable page) {
        return mkPageContents(repo.findAllByEmployeeID(employeeID, page));
    }

    public Map<String, Object> getAllByEmployeeIDAndStatus(
            UUID id, ReimbursementStatus status, Pageable page
    ) {
        return mkPageContents(repo.findByAllByEmployeeIDAndStatus(id, status, page));
    }

    private Map<String, Object> mkPageContents(Page<ReimbursementTicket> results) {
        Map<String, Object> pageContents = new HashMap<>();

        pageContents.put("tickets", results == null ? null : results.getContent());
        pageContents.put("currentPage", results == null ? 0 : results.getNumber());
        pageContents.put("totalItems", results == null ? 0 : results.getTotalElements());
        pageContents.put("totalPages", results == null ? 0 : results.getTotalPages());

        return pageContents;
    }
}
