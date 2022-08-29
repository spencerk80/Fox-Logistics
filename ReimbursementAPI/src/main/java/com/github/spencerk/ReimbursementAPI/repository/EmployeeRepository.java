package com.github.spencerk.ReimbursementAPI.repository;

import com.github.spencerk.ReimbursementAPI.Entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface EmployeeRepository extends MongoRepository<Employee, UUID> {
}
