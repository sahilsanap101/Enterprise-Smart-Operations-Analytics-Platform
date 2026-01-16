package com.enterprise.ops.backend.employee;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enterprise.ops.backend.user.User;
import com.enterprise.ops.backend.user.UserRepository;


/*
 * REST controller for Employee APIs
 */

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
/*
     * CREATE employee
     * Only ADMIN can create employees
*/
@PostMapping
@PreAuthorize("hasAuthority('ADMIN')")
public ResponseEntity<Employee> createEmployee(
    @RequestBody EmployeeRequest request
)
{
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

@GetMapping
@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
public ResponseEntity<List<Employee>> getAllEmployees(){
    return ResponseEntity.ok(employeeRepository.findAll());
}
}
