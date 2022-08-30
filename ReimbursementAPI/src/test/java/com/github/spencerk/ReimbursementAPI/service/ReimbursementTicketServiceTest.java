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
import org.springframework.data.domain.*;
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

    private List<ReimbursementTicket> allTickets = Stream.of(
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 85.35f,
                    ReimbursementCategory.GAS, "", ReimbursementStatus.PENDING
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 1060f,
                    ReimbursementCategory.LODGING, "", ReimbursementStatus.ACCEPTED
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 23.48f,
                    ReimbursementCategory.MEAL, "", ReimbursementStatus.DENIED
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 85.35f,
                    ReimbursementCategory.GAS, "", ReimbursementStatus.PENDING
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 560f,
                    ReimbursementCategory.LODGING, "", ReimbursementStatus.ACCEPTED
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 23.48f,
                    ReimbursementCategory.MEAL, "", ReimbursementStatus.DENIED
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 85.35f,
                    ReimbursementCategory.GAS, "", ReimbursementStatus.PENDING
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 560f,
                    ReimbursementCategory.LODGING, "", ReimbursementStatus.ACCEPTED
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 23.48f,
                    ReimbursementCategory.MEAL, "", ReimbursementStatus.DENIED
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 85.35f,
                    ReimbursementCategory.GAS, "", ReimbursementStatus.PENDING
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 560f,
                    ReimbursementCategory.LODGING, "", ReimbursementStatus.ACCEPTED
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 23.48f,
                    ReimbursementCategory.MEAL, "", ReimbursementStatus.DENIED
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 85.35f,
                    ReimbursementCategory.GAS, "", ReimbursementStatus.PENDING
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 560f,
                    ReimbursementCategory.LODGING, "", ReimbursementStatus.ACCEPTED
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 23.48f,
                    ReimbursementCategory.MEAL, "", ReimbursementStatus.DENIED
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 85.35f,
                    ReimbursementCategory.GAS, "", ReimbursementStatus.PENDING
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 560f,
                    ReimbursementCategory.LODGING, "", ReimbursementStatus.ACCEPTED
            ),
            new ReimbursementTicket(
                    "6143b03e-ddf1-48d8-9768-e0b270f89bc3", 23.48f,
                    ReimbursementCategory.MEAL, "", ReimbursementStatus.DENIED
            )
    ).collect(Collectors.toList());

    @Test
    public void getPageOfAllTickets() {
        final int page = 0, pageSize = 10;
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("timeStamp")));
        Page<ReimbursementTicket> pageOfData;
        List<ReimbursementTicket> pagedList = allTickets.subList(page, (page + 1) * pageSize);
        Map<String, Object> responseContent;

        Collections.reverse(pagedList);
        pageOfData = new PageImpl<ReimbursementTicket>(allTickets, paging, allTickets.size());

        when(repo.findAll(paging)).thenReturn(pageOfData);
        responseContent = service.getAllTickets(paging);

        assertEquals(pagedList, (List<ReimbursementTicket>) responseContent.get("tickets"));
        assertEquals(0, responseContent.get("currentPage"));
        assertEquals(10l, responseContent.get("totalItems"));
        assertEquals(2, responseContent.get("totalPages"));
    }
}
