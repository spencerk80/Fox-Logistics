package com.github.spencerk.ReimbursementAPI.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ReimbursementTicketTest {
    @Test
    public void idsAreNotEqual() {
        ReimbursementTicket t1 = new ReimbursementTicket(), t2 = new ReimbursementTicket();

        assertNotEquals(t1, t2);
    }
}
