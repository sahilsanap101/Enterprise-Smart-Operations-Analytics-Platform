package com.enterprise.ops.backend.employee;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.enterprise.ops.backend.user.User;
import com.enterprise.ops.backend.user.UserRepository;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public EmployeeController(
            EmployeeService employeeService,
            EmployeeRepository employeeRepository,
            UserRepository userRepository
    ) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    // ADMIN → create employee
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Employee> createEmployee(
            @Valid @RequestBody EmployeeRequest request
    ) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee employee = employeeService.createEmployee(
                request.getName(),
                request.getDesignation(),
                request.getDepartment(),
                user
        );

        return ResponseEntity.ok(employee);
    }

    // ADMIN + MANAGER
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    // EMPLOYEE → own profile
    @GetMapping("/me")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<EmployeeResponse> getMyProfile() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(
                employeeService.getMyProfile(email)
        );
    }
}
