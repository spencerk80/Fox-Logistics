package com.github.spencerk.ReimbursementAPI.service;

import com.github.spencerk.ReimbursementAPI.entity.ReimbursementTicket;
import com.github.spencerk.ReimbursementAPI.enums.ReimbursementCategory;
import com.github.spencerk.ReimbursementAPI.enums.ReimbursementStatus;
import com.github.spencerk.ReimbursementAPI.repository.ReimbursementTicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {ReimbursementTicketService.class, ReimbursementTicketRepository.class})
public class ReimbursementTicketServiceTest {
    @Autowired
    private ReimbursementTicketService service;
    @MockBean
    private ReimbursementTicketRepository repo;

    private ReimbursementTicket pendingTicket = new ReimbursementTicket(
                                        UUID.fromString("6143b03e-ddf1-48d8-9768-e0b270f89bc3"), 85.35f,
                                        ReimbursementCategory.GAS, "", ReimbursementStatus.PENDING
                                ),
                                acceptedTicket = new ReimbursementTicket(
                                        UUID.fromString("6143b03e-ddf1-48d8-9768-e0b270f89bc3"), 560f,
                                        ReimbursementCategory.LODGING, "", ReimbursementStatus.ACCEPTED
                                ),
                                deniedTicket = new ReimbursementTicket(
                                        UUID.fromString("6143b03e-ddf1-48d8-9768-e0b270f89bc3"), 23.48f,
                                        ReimbursementCategory.MEAL, "", ReimbursementStatus.DENIED
                                );

    private List<ReimbursementTicket> allTickets = Stream.of(
            pendingTicket, acceptedTicket, deniedTicket
    ).collect(Collectors.toList());

    @Test
    public void getPageOfAllTickets() {
        Pageable paging = PageRequest.of(0, 10);
        Map<String, Object> responseContent;

        when(repo.findAll()).thenReturn(allTickets);
        responseContent = service.getAllTickets(paging);

        assertEquals(allTickets, (List<ReimbursementTicket>) responseContent.get("tickets"));
        assertEquals(0, responseContent.get("currentPage"));
        assertEquals(3, responseContent.get("totalItems"));
        assertEquals(1, responseContent.get("totalPages"));
    }
}
