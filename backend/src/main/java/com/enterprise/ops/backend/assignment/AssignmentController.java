package com.enterprise.ops.backend.assignment;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.enterprise.ops.backend.employee.Employee;
import com.enterprise.ops.backend.employee.EmployeeRepository;
import com.enterprise.ops.backend.project.Project;
import com.enterprise.ops.backend.project.ProjectRepository;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;

    public AssignmentController(
            AssignmentService assignmentService,
            EmployeeRepository employeeRepository,
            ProjectRepository projectRepository
    ) {
        this.assignmentService = assignmentService;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    // MANAGER only
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Assignment> assignEmployee(
            @Valid @RequestBody AssignmentRequest request
    ) {
        Employee employee = employeeRepository.findById(
                request.getEmployeeId()
        ).orElseThrow(() -> new RuntimeException("Employee not found"));

        Project project = projectRepository.findById(
                request.getProjectId()
        ).orElseThrow(() -> new RuntimeException("Project not found"));

        Assignment assignment = assignmentService.assignEmployee(
                employee,
                project,
                request.getAllocationPercentage()
        );

        return ResponseEntity.ok(assignment);
    }

    @GetMapping("/project/{projectId}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<Assignment>> getAssignmentsByProject(
            @PathVariable Long projectId
    ) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return ResponseEntity.ok(
                assignmentService.getAssignmentsByProject(project)
        );
    }

    @GetMapping("/employee/{employeeId}/utilization")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<Integer> getEmployeeUtilization(
            @PathVariable Long employeeId
    ) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return ResponseEntity.ok(
                assignmentService.getEmployeeUtilization(employee)
        );
    }
}
