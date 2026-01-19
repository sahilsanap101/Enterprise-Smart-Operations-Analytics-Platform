package com.enterprise.ops.backend.assignment;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.enterprise.ops.backend.employee.Employee;
import com.enterprise.ops.backend.project.Project;

/*
 * Business logic for Assignment & utilization
 */
@Service
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    /*
     * Assign employee to project with utilization check
     */
    public Assignment assignEmployee(
            Employee employee,
            Project project,
            int allocationPercentage
    ) {
        int currentAllocation = assignmentRepository
                .findByEmployee(employee)
                .stream()
                .mapToInt(Assignment::getAllocationPercentage)
                .sum();

        if (currentAllocation + allocationPercentage > 100) {
            throw new RuntimeException(
                    "Employee allocation exceeds 100%"
            );
        }

        Assignment assignment = new Assignment();
        assignment.setEmployee(employee);
        assignment.setProject(project);
        assignment.setAllocationPercentage(allocationPercentage);
        assignment.setAssignedDate(LocalDate.now());

        return assignmentRepository.save(assignment);
    }

    public List<Assignment> getAssignmentsByProject(Project project) {
        return assignmentRepository.findByProject(project);
    }

    public int getEmployeeUtilization(Employee employee) {
        return assignmentRepository
                .findByEmployee(employee)
                .stream()
                .mapToInt(Assignment::getAllocationPercentage)
                .sum();
    }
}
