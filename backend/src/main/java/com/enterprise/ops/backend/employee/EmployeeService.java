package com.enterprise.ops.backend.employee;

import org.springframework.stereotype.Service;

import com.enterprise.ops.backend.user.User;

/*
 * Service layer contains business logic related to Employee.
 * It sits between Controller and Repository.
 */
@Service
public class EmployeeService {

    // Repository dependency (used to talk to database)
    private final EmployeeRepository employeeRepository;

    /*
     * Constructor injection (BEST PRACTICE)
     * Spring automatically injects EmployeeRepository bean here.
     */
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /*
     * Create a new employee linked to a User.
     *
     * Flow:
     * 1. Create Employee entity
     * 2. Set business fields
     * 3. Link Employee to User (foreign key relationship)
     * 4. Persist to database
     */
    public Employee createEmployee(
            String name,
            String designation,
            String department,
            User user
    ) {
        Employee employee = new Employee();

        employee.setName(name);
        employee.setDesignation(designation);
        employee.setDepartment(department);
        employee.setUser(user);

        // Save employee record in database
        return employeeRepository.save(employee);
    }
}
