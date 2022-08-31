package com.github.spencerk.ReimbursementAPI.service;

import com.github.spencerk.ReimbursementAPI.entity.ReimbursementTicket;
import com.github.spencerk.ReimbursementAPI.enums.ReimbursementStatus;
import com.github.spencerk.ReimbursementAPI.exceptions.ResourceNotFoundException;
import com.github.spencerk.ReimbursementAPI.repository.ReimbursementTicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;


@Service
public class ReimbursementTicketService {
    private final Logger                        logger;
    private final ReimbursementTicketRepository repo;

    @Autowired
    public ReimbursementTicketService(ReimbursementTicketRepository repo) {
        this.repo = repo;
        this.logger = LoggerFactory.getLogger(ReimbursementTicketService.class);
    }

    public void saveTicket(ReimbursementTicket ticket) {
        logger.trace("ReimbursementTicketService.saveTicket(ticket)");

        this.repo.save(ticket);
    }

    public void updateTicket(ReimbursementTicket ticket) throws ResourceNotFoundException {
        logger.trace("ReimbursementTicketService.updateTicket(ticket)");

        if(repo.findById(ticket.getId()).isEmpty()) {
            logger.error("No existing record for ticket-to-be-updated found");

            throw new ResourceNotFoundException("Existing ticket now found to update");
        }

        repo.save(ticket);
    }

    public Map<String, Object> getAllTickets(Pageable page) {
        logger.trace("ReimbursementTicketService.getAllTickets(page)");

        return mkPageContents(repo.findAll(page));
    }

    public Map<String, Object> getAllByStatus(ReimbursementStatus status, Pageable page) {
        logger.trace("ReimbursementTicketService.getAllByStatus(status, page)");

        return mkPageContents(repo.findByStatus(status, page));
    }

    public Map<String, Object> getAllByEmployeeId(String employeeID, Pageable page) {
        logger.trace("ReimbursementTicketService.getAllByEmployeeId(employeeID, page)");

        return mkPageContents(repo.findByEmployeeId(employeeID, page));
    }

    public Map<String, Object> getAllByEmployeeIdAndStatus(
            String id, ReimbursementStatus status, Pageable page
    ) {
        logger.trace("ReimbursementTicketService.getAllByEmployeeIdAndStatus(employeeID, status, page)");

        return mkPageContents(repo.findByEmployeeIdAndStatus(id, status, page));
    }

    private Map<String, Object> mkPageContents(Page<ReimbursementTicket> results) {
        Map<String, Object> pageContents = new HashMap<>();

        logger.trace("ReimbursementTicketService.mkPageContents(pageOfResults)");

        pageContents.put("tickets", results == null ? null : results.getContent());
        pageContents.put("currentPage", results == null ? 0 : results.getNumber());
        pageContents.put("totalItems", results == null ? 0 : results.getTotalElements());
        pageContents.put("totalPages", results == null ? 0 : results.getTotalPages());

        return pageContents;
    }
}
