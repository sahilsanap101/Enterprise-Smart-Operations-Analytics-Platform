package com.enterprise.ops.backend.analytics;

import java.util.List;

import org.springframework.stereotype.Service;

import com.enterprise.ops.backend.analytics.dto.AlertResponse;
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

    // USED BY EmployeeAnalyticsController
    public List<EmployeeUtilizationResponse> getEmployeeUtilizationAnalytics() {

        return employeeRepository.findAll()
                .stream()
                .map(this::mapUtilization)
                .toList();
    }

    // USED BY AlertAnalyticsService
    public List<AlertResponse> getEmployeeAlerts() {

        return employeeRepository.findAll()
                .stream()
                .map(this::checkEmployee)
                .filter(a -> a != null)
                .toList();
    }

    private EmployeeUtilizationResponse mapUtilization(Employee employee) {

        int utilization = assignmentRepository
                .findByEmployee(employee)
                .stream()
                .mapToInt(Assignment::getAllocationPercentage)
                .sum();

        String status =
                utilization >= 80 ? "OVERLOADED"
                : utilization <= 40 ? "UNDERUTILIZED"
                : "OPTIMAL";

        return new EmployeeUtilizationResponse(
                employee.getId(),
                employee.getName(),
                employee.getDepartment(),
                utilization,
                status
        );
    }

    private AlertResponse checkEmployee(Employee employee) {

        int utilization = assignmentRepository
                .findByEmployee(employee)
                .stream()
                .mapToInt(Assignment::getAllocationPercentage)
                .sum();

        if (utilization >= 80) {
            return new AlertResponse(
                    "EMPLOYEE_OVERLOADED",
                    employee.getName() + " is overloaded (" + utilization + "%)"
            );
        }

        if (utilization <= 40) {
            return new AlertResponse(
                    "EMPLOYEE_UNDERUTILIZED",
                    employee.getName() + " is underutilized (" + utilization + "%)"
            );
        }

        return null;
    }
}