package com.github.spencerk.ReimbursementAPI.service;

import com.github.spencerk.ReimbursementAPI.entity.Employee;
import com.github.spencerk.ReimbursementAPI.exceptions.ResourceNotFoundException;
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

    public Employee getEmployeeByUsername(String username) {
        Optional<Employee> employee = repo.findByUsername(username);
        return employee.isPresent() ? employee.get() : null;
    }

    public Employee getEmployeeByID(String id) {
        Optional<Employee> employee = repo.findById(id);

        return employee.isPresent() ? employee.get() : null;
    }

    public void updateEmployee(Employee employee) throws ResourceNotFoundException {
        //Provided ID must match an existing record, or bad things happen i.e. data duplication with new IDs
        if(repo.findById(employee.getId()).isPresent())
            repo.save(employee);
        else
            throw new ResourceNotFoundException("Employee does not exist!");
    }

    public void deleteEmployeeByID(String id) {
        repo.deleteById(id);
    }

    public void deleteEmployeeByUsername(String username) {
        repo.deleteByUsername(username);
    }
}
