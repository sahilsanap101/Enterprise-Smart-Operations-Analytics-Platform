package com.enterprise.ops.backend.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enterprise.ops.backend.employee.Employee;
import com.enterprise.ops.backend.project.Project;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedEmployee(Employee employee);

    List<Task> findByProject(Project project);
}
