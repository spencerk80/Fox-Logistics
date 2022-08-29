package com.github.spencerk.ReimbursementAPI.service;

import com.github.spencerk.ReimbursementAPI.entity.Employee;
import com.github.spencerk.ReimbursementAPI.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {
    private EmployeeRepository repo;

    @Autowired
    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }

    public void saveEmployee(Employee employee) {
        repo.save(employee);
    }

    public Employee getEmployee(String username) {
        Optional<Employee> employee = repo.findByUsername(username);
        return employee.isPresent() ? employee.get() : null;
    }

    public Employee getEmployee(UUID id) {
        Optional<Employee> employee = repo.findById(id);

        return employee.isPresent() ? employee.get() : null;
    }

    public void updateEmployee(Employee employee) {
        repo.save(employee);
    }

    public void deleteEmployee(UUID id) {
        repo.deleteById(id);
    }

    public void deleteEmployee(String username) {
        repo.deleteByUsername(username);
    }
}
