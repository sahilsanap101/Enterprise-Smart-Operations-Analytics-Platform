package com.enterprise.ops.backend.analytics;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.enterprise.ops.backend.analytics.dto.AlertResponse;

@Service
public class AlertAnalyticsService {

    private final EmployeeAnalyticsService employeeAnalyticsService;

    public AlertAnalyticsService(
            EmployeeAnalyticsService employeeAnalyticsService
    ) {
        this.employeeAnalyticsService = employeeAnalyticsService;
    }

    public List<AlertResponse> getAllAlerts() {

        List<AlertResponse> alerts = new ArrayList<>();
        alerts.addAll(employeeAnalyticsService.getEmployeeAlerts());

        return alerts;
    }
}