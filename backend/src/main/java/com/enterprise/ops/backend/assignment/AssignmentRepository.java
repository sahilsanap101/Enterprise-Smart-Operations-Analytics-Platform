package com.enterprise.ops.backend.assignment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.ops.backend.employee.Employee;
import com.enterprise.ops.backend.project.Project;

/*
 * Repository for Assignment entity
 */
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByEmployee(Employee employee);

    List<Assignment> findByProject(Project project);
}
