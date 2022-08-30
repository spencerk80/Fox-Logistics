package com.github.spencerk.ReimbursementAPI.repository;

import com.github.spencerk.ReimbursementAPI.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Optional<Employee> findByUsername(String username);
    void deleteByUsername(String username);
}
