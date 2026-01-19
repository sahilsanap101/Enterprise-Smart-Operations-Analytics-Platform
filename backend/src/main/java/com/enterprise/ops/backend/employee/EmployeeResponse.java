package com.enterprise.ops.backend.employee;

public record EmployeeResponse(
        Long id,
        String name,
        String designation,
        String department,
        String email,
        String role
) {}
