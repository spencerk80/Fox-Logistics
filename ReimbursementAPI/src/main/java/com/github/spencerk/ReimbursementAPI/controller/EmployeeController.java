package com.github.spencerk.ReimbursementAPI.controller;

import com.github.spencerk.ReimbursementAPI.entity.Employee;
import com.github.spencerk.ReimbursementAPI.exceptions.ResourceNotFoundException;
import com.github.spencerk.ReimbursementAPI.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class EmployeeController {
    private final Logger logger;
    private final EmployeeService employeeService;
    private final PasswordEncoder encoder;

    @Autowired
    public EmployeeController(EmployeeService service, PasswordEncoder encoder) {
        this.employeeService = service;
        this.encoder = encoder;
        this.logger = LoggerFactory.getLogger(EmployeeController.class);
    }

    @PostMapping("employees/register")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        logger.trace("Incoming POST request to /api/employees/register");

        if( ! employee.validate()) {
            logger.error("Employee data from request is invalid!");

            return ResponseEntity.badRequest().body("Invalid data sent");
        }

        employee.setPassword(encoder.encode(employee.getPassword()));
        employeeService.saveEmployee(employee);

        logger.info("Returning register employee response");

        return ResponseEntity.created(URI.create("api/employees")).body(null);
    }

    @GetMapping("employees/username/{username}")
    public ResponseEntity<Employee> getEmployeeByUsername(@PathVariable String username) {
        logger.trace(String.format("Incoming GET request to /api/employees/username/%s", username));
        logger.info("Returning get employee by username response");

        return ResponseEntity.ok().body(employeeService.getEmployeeByUsername(username));
    }

    @GetMapping("employees/id/{id}")
    public ResponseEntity<Employee> getEmployeeByID(@PathVariable String id) {
        logger.trace(String.format("Incoming GET request to /api/employees/id/%s", id));
        logger.info("Returning get employee by id response");

        return ResponseEntity.ok().body(employeeService.getEmployeeByID(id));
    }

    @PutMapping("employees")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employee) {
        logger.trace("Incoming PUT request to /api/employees");

        if( ! employee.validate()) {
            logger.error("Employee data from request is invalid!");

            return ResponseEntity.badRequest().body("Invalid data sent");
        }

        employee.setPassword(encoder.encode(employee.getPassword()));

        try {
            employeeService.updateEmployee(employee);
        } catch(ResourceNotFoundException e) {
            logger.error("Entry for employee to be updated doesn't exist!");

            return ResponseEntity.badRequest().body("Employee does not exist to update");
        }

        logger.info("Returning update employee response");

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("employees/username/{username}")
    public ResponseEntity<String> deleteEmployeeByUsername(@PathVariable String username) {
        logger.trace(String.format("Incoming DELETE request to /api/employees/username/%s", username));

        employeeService.deleteEmployeeByUsername(username);

        logger.info("Returning delete employee response");

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("employees/id/{id}")
    public ResponseEntity<String> deleteEmployeeByID(@PathVariable String id) {
        logger.trace(String.format("Incoming DELETE request to /api/employees/id/%s", id));

        employeeService.deleteEmployeeByID(id);

        logger.info("Returning delete employee response");

        return ResponseEntity.ok().body(null);
    }
}
