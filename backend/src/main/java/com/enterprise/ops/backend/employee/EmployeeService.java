package com.enterprise.ops.backend.employee;

import org.springframework.stereotype.Service;

import com.enterprise.ops.backend.user.User;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

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
        employee.setActive(true);

        return employeeRepository.save(employee);
    }

    public EmployeeResponse getMyProfile(String email) {

        Employee employee = employeeRepository
                .findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getDesignation(),
                employee.getDepartment(),
                employee.getUser().getEmail(),
                employee.getUser().getRole().name()
        );
    }
}
