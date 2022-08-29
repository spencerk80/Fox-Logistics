package com.github.spencerk.ReimbursementAPI.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EmployeeTest {
    @Test
    public void idsAreUnique() {
        Employee e1 = new Employee(), e2 = new Employee();

        assertNotEquals(e1,e2);
    }
}
