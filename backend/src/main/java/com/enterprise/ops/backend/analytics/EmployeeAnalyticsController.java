package com.enterprise.ops.backend.analytics;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics/employees")
public class EmployeeAnalyticsController {

    private final EmployeeAnalyticsService analyticsService;

    public EmployeeAnalyticsController(
            EmployeeAnalyticsService analyticsService
    ) {
        this.analyticsService = analyticsService;
    }

    // ADMIN & MANAGER only
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<EmployeeUtilizationResponse>>
    getEmployeeAnalytics() {

        return ResponseEntity.ok(
                analyticsService.getEmployeeUtilizationAnalytics()
        );
    }
}
