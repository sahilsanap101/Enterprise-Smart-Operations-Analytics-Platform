package com.enterprise.ops.backend.analytics;

public record EmployeeUtilizationResponse(
        Long employeeId,
        String employeeName,
        String department,
        int utilizationPercentage,
        String utilizationStatus
) {}
