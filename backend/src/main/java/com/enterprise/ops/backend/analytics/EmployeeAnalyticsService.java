package com.enterprise.ops.backend.analytics;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enterprise.ops.backend.assignment.Assignment;
import com.enterprise.ops.backend.assignment.AssignmentRepository;
import com.enterprise.ops.backend.employee.Employee;
import com.enterprise.ops.backend.employee.EmployeeRepository;

@Service
public class EmployeeAnalyticsService {

    private final EmployeeRepository employeeRepository;
    private final AssignmentRepository assignmentRepository;

    public EmployeeAnalyticsService(
            EmployeeRepository employeeRepository,
            AssignmentRepository assignmentRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public List<EmployeeUtilizationResponse> getEmployeeUtilizationAnalytics() {

        return employeeRepository.findAll()
                .stream()
                .map(this::mapToUtilization)
                .toList();
    }

    private EmployeeUtilizationResponse mapToUtilization(Employee employee) {

        int utilization = assignmentRepository
                .findByEmployee(employee)
                .stream()
                .mapToInt(Assignment::getAllocationPercentage)
                .sum();

        String status;

        if (utilization >= 80) {
            status = "OVERLOADED";
        } else if (utilization <= 40) {
            status = "UNDERUTILIZED";
        } else {
            status = "OPTIMAL";
        }

        return new EmployeeUtilizationResponse(
                employee.getId(),
                employee.getName(),
                employee.getDepartment(),
                utilization,
                status
        );
    }
}
