package com.enterprise.ops.backend.employee;


import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Repository for Employee entity.
 * Handles DB operations automatically.
 */

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
}
