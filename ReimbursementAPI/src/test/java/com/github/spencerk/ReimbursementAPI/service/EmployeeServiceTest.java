package com.github.spencerk.ReimbursementAPI.service;

import com.github.spencerk.ReimbursementAPI.entity.Employee;
import com.github.spencerk.ReimbursementAPI.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {EmployeeService.class, EmployeeRepository.class})
public class EmployeeServiceTest {
    @Autowired
    private EmployeeService     service;
    @MockBean
    private EmployeeRepository  repo;

    private final String        testID = "6143b03e-ddf1-48d8-9768-e0b270f89bc3";
    private Employee testEmployee = new Employee(
            UUID.fromString(testID), "b.smith", "Bob", "Smith", "b.smith@email.com",
            "555-555-5555", "AB38E0C7D"
    );

    @Test
    public void getEmployeeByUsername() {
        when(repo.findByUsername(testEmployee.getUsername())).thenReturn(Optional.of(testEmployee));

        assertEquals(testEmployee, service.getEmployee(testEmployee.getUsername()));
    }

    @Test
    public void getEmployeeByID() {
        when(repo.findById(UUID.fromString(testID))).thenReturn(Optional.of(testEmployee));

        assertEquals(testEmployee, service.getEmployee(UUID.fromString(testID)));
    }
}
