package com.github.spencerk.ReimbursementAPI.service;

import com.github.spencerk.ReimbursementAPI.entity.Employee;
import com.github.spencerk.ReimbursementAPI.exceptions.ResourceNotFoundException;
import com.github.spencerk.ReimbursementAPI.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    private final Logger                logger;
    private final EmployeeRepository    repo;

    @Autowired
    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
        this.logger = LoggerFactory.getLogger(EmployeeService.class);
    }

    public void saveEmployee(Employee employee) {
        logger.trace("EmployeeService.saveEmployee(employee)");

        repo.save(employee);
    }

    public Employee getEmployeeByUsername(String username) {
        Optional<Employee> employee = repo.findByUsername(username);

        logger.trace("EmployeeService.getEmployeeByUsername(username)");

        return employee.orElse(null);
    }

    public Employee getEmployeeByID(String id) {
        Optional<Employee> employee = repo.findById(id);

        logger.trace("EmployeeService.getEmployeeByID(id)");

        return employee.orElse(null);
    }

    public void updateEmployee(Employee employee) throws ResourceNotFoundException {
        logger.trace("EmployeeService.updateEmployee(employee)");

        //Provided ID must match an existing record, or bad things happen i.e. data duplication with new IDs
        if(repo.findById(employee.getId()).isPresent())
            repo.save(employee);
        else {
            logger.error("No existing record for the employee-to-be-updated found");

            throw new ResourceNotFoundException("Employee does not exist!");
        }
    }

    public void deleteEmployeeByID(String id) {
        logger.trace("EmployeeService.deleteEmployeeByID(id)");

        repo.deleteById(id);
    }

    public void deleteEmployeeByUsername(String username) {
        logger.trace("EmployeeService.deleteEmployeeByUsername(username)");

        repo.deleteByUsername(username);
    }
}
