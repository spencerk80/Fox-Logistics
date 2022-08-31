package com.github.spencerk.ReimbursementAPI.controller;

import com.github.spencerk.ReimbursementAPI.entity.Employee;
import com.github.spencerk.ReimbursementAPI.exceptions.ResourceNotFoundException;
import com.github.spencerk.ReimbursementAPI.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api")
public class EmployeeController {
    private EmployeeService employeeService;
    private PasswordEncoder encoder;

    @Autowired
    public EmployeeController(EmployeeService service, PasswordEncoder encoder) {
        this.employeeService = service;
        this.encoder = encoder;
    }

    @PostMapping("employees/register")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee) {
        if( ! employee.validate()) return ResponseEntity.badRequest().body("Invalid data sent");

        employee.setPassword(encoder.encode(employee.getPassword()));
        employeeService.saveEmployee(employee);

        return ResponseEntity.created(URI.create("api/employees")).body(null);
    }

    @GetMapping("employees/username/{username}")
    public ResponseEntity<Employee> getEmployeeByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(employeeService.getEmployeeByUsername(username));
    }

    @GetMapping("employees/id/{id}")
    public ResponseEntity<Employee> getEmployeeByID(@PathVariable String id) {
        return ResponseEntity.ok().body(employeeService.getEmployeeByID(id));
    }

    @PutMapping("employees")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee employee) {
        if( ! employee.validate()) return ResponseEntity.badRequest().body("Invalid data sent");

        employee.setPassword(encoder.encode(employee.getPassword()));

        try {
            employeeService.updateEmployee(employee);
        } catch(ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body("Employee does not exist to update");
        }

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("employees/username/{username}")
    public ResponseEntity<String> deleteEmployeeByUsername(@PathVariable String username) {
        employeeService.deleteEmployeeByUsername(username);

        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("employees/id/{id}")
    public ResponseEntity<String> deleteEmployeeByID(@PathVariable String id) {
        employeeService.deleteEmployeeByID(id);

        return ResponseEntity.ok().body(null);
    }
}
