package com.github.spencerk.ReimbursementAPI.controller;

import com.github.spencerk.ReimbursementAPI.entity.ReimbursementTicket;
import com.github.spencerk.ReimbursementAPI.enums.ReimbursementStatus;
import com.github.spencerk.ReimbursementAPI.exceptions.ResourceNotFoundException;
import com.github.spencerk.ReimbursementAPI.service.ReimbursementTicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:3000")
public class ReimbursementTicketController {
    private final Logger                        logger;
    private final ReimbursementTicketService    service;

    @Autowired
    public ReimbursementTicketController(ReimbursementTicketService service) {
        this.service = service;
        this.logger = LoggerFactory.getLogger(ReimbursementTicketController.class);
    }

    @PostMapping("tickets")
    public ResponseEntity<String> saveTicket(@RequestBody ReimbursementTicket ticket) throws URISyntaxException {
        logger.trace("Incoming POST request to /api/tickets");

        if( ! ticket.validate()) {
            logger.error("Ticket info is invalid!");

            return ResponseEntity.badRequest().body("Ticket data is invalid!");
        }
        service.saveTicket(ticket);
        logger.info("Returning create new ticket response");

        return ResponseEntity.created(new URI("api/tickets")).body(null);
    }

    @PutMapping("tickets")
    public ResponseEntity<String> updateTicket(@RequestBody ReimbursementTicket ticket) {
        logger.trace("Incoming Put request to /api/tickets");

        if( ! ticket.validate()) {
            logger.error("Ticket info is invalid!");

            return ResponseEntity.badRequest().body("Ticket data is invalid!");
        }
        try {
            service.updateTicket(ticket);
        } catch(ResourceNotFoundException e) {
            logger.error("No existing ticket to update with new info");

            return ResponseEntity.badRequest().body(e.getMessage());
        }
        logger.info("Returning update ticket response");

        return ResponseEntity.ok().body(null);
    }

    /******************************************************************************************
     * Manager level access. Get tickets of all users
     ******************************************************************************************/
    @GetMapping("tickets/{page}/{limit}")
    public ResponseEntity<Map<String, Object>> getAllTickets(@PathVariable int page, @PathVariable int limit) {
        Pageable paging = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("timeStamp")));

        logger.trace(String.format("Incoming GET request to /api/tickets/%d/%d", page, limit));
        logger.info("Returning a page of all employee tickets in response");

        return ResponseEntity.ok().body(service.getAllTickets(paging));
    }

    @GetMapping("tickets/status/{status}/{page}/{limit}")
    public ResponseEntity<Map<String, Object>> getAllTicketsByStatus(
            @PathVariable String status, @PathVariable int page, @PathVariable int limit
    ) {
        Pageable paging = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("timeStamp")));

        logger.trace(String.format("Incoming GET request to /api/tickets/status/%s/%d/%d", status, page, limit));

        try {
            logger.info("Formulating response to return a page of all employee tickets filtered by status");

            return ResponseEntity.ok().body(service.getAllByStatus(ReimbursementStatus.valueOf(status), paging));
        } catch(IllegalArgumentException e) {
            logger.error("Invalid status provided!");

            return ResponseEntity.badRequest().body(null);
        }
    }

    /******************************************************************************************
     * Staff level access. Get tickets for a specific user. (Only their own)
     ******************************************************************************************/

    @GetMapping("tickets/employeeID/{id}/{page}/{limit}")
    public ResponseEntity<Map<String, Object>> getAllTicketsByEmployeeID(
            @PathVariable String id, @PathVariable int page, @PathVariable int limit
    ) {
        Pageable paging = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("timeStamp")));

        logger.trace(String.format("Incoming GET request to /api/tickets/employeeID/%s/%d/%d", id, page, limit));
        logger.info("Returning a page of the employee's tickets in response");

        return ResponseEntity.ok().body(service.getAllByEmployeeId(id, paging));
    }

    @GetMapping("tickets/statusByEmployeeID/{id}/{status}/{page}/{limit}")
    public ResponseEntity<Map<String, Object>> getAllTicketsByStatusAndEmployeeID(
            @PathVariable String id, @PathVariable String status, @PathVariable int page, @PathVariable int limit
    ) {
        Pageable paging = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("timeStamp")));

        logger.trace(String.format(
                "Incoming GET request to /api/tickets/statusByEmployeeID/%s/%s/%d/%d", id, status, page, limit
        ));

        try {
            logger.info("Formulating response to return a page of the employee's tickets filtered by status");

            return ResponseEntity.ok().body(
                    service.getAllByEmployeeIdAndStatus(id, ReimbursementStatus.valueOf(status), paging)
            );
        } catch(IllegalArgumentException e) {
            logger.error("Invalid status provided!");

            return ResponseEntity.badRequest().body(null);
        }
    }
}
