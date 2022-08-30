package com.github.spencerk.ReimbursementAPI.controller;

import com.github.spencerk.ReimbursementAPI.entity.ReimbursementTicket;
import com.github.spencerk.ReimbursementAPI.enums.ReimbursementStatus;
import com.github.spencerk.ReimbursementAPI.exceptions.ResourceNotFoundException;
import com.github.spencerk.ReimbursementAPI.service.ReimbursementTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static org.springframework.data.domain.Sort.Order.desc;

@RestController
@RequestMapping("api")
public class ReimbursementTicketController {
    private ReimbursementTicketService service;

    @Autowired
    public ReimbursementTicketController(ReimbursementTicketService service) {
        this.service = service;
    }

    @PostMapping("tickets")
    public ResponseEntity<String> saveTicket(@RequestBody ReimbursementTicket ticket) throws URISyntaxException {
        if( ! ticket.validate()) return ResponseEntity.badRequest().body("Ticket data is invalid!");
        service.saveTicket(ticket);

        return ResponseEntity.created(new URI("api/tickets")).body(null);
    }

    @PutMapping("tickets")
    public ResponseEntity<String> updateTicket(@RequestBody ReimbursementTicket ticket) {
        if( ! ticket.validate()) return ResponseEntity.badRequest().body("Ticket data is invalid!");
        try {
            service.updateTicket(ticket);
        } catch(ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("tickets/{page}/{limit}")
    public ResponseEntity<Map<String, Object>> getAllTickets(@PathVariable int page, @PathVariable int limit) {
        Pageable paging = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("timeStamp")));

        return ResponseEntity.ok().body(service.getAllTickets(paging));
    }

    @GetMapping("tickets/status/{status}/{page}/{limit}")
    public ResponseEntity<Map<String, Object>> getAllTicketsByStatus(
            @PathVariable String status, @PathVariable int page, @PathVariable int limit
    ) {
        Pageable paging = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("timeStamp")));

        try {
            return ResponseEntity.ok().body(service.getAllByStatus(ReimbursementStatus.valueOf(status), paging));
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("tickets/employeeID/{id}/{page}/{limit}")
    public ResponseEntity<Map<String, Object>> getAllTicketsByEmployeeID(
            @PathVariable String id, @PathVariable int page, @PathVariable int limit
    ) {
        Pageable paging = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("timeStamp")));

        return ResponseEntity.ok().body(service.getAllByEmployeeId(id, paging));
    }

    @GetMapping("tickets/statusByEmployeeID/{id}/{status}/{page}/{limit}")
    public ResponseEntity<Map<String, Object>> getAllTicketsByStatusAndEmployeeID(
            @PathVariable String id, @PathVariable String status, @PathVariable int page, @PathVariable int limit
    ) {
        Pageable paging = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("timeStamp")));

        try {
            return ResponseEntity.ok().body(
                    service.getAllByEmployeeIdAndStatus(id, ReimbursementStatus.valueOf(status), paging)
            );
        } catch(IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
