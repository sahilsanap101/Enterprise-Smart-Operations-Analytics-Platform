package com.enterprise.ops.backend.analytics;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.enterprise.ops.backend.analytics.dto.AlertResponse;
import com.enterprise.ops.backend.analytics.dto.ProjectRiskResponse;
import com.enterprise.ops.backend.analytics.dto.ProjectWorkloadResponse;
import com.enterprise.ops.backend.analytics.dto.TicketTrendResponse;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final TicketAnalyticsService ticketAnalyticsService;
    private final ProjectAnalyticsService projectAnalyticsService;
    private final AlertAnalyticsService alertAnalyticsService;

    public AnalyticsController(
            TicketAnalyticsService ticketAnalyticsService,
            ProjectAnalyticsService projectAnalyticsService,
            AlertAnalyticsService alertAnalyticsService
    ) {
        this.ticketAnalyticsService = ticketAnalyticsService;
        this.projectAnalyticsService = projectAnalyticsService;
        this.alertAnalyticsService = alertAnalyticsService;
    }

    @GetMapping("/tickets/trends")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<TicketTrendResponse>> ticketTrends() {
        return ResponseEntity.ok(
                ticketAnalyticsService.getTicketTrends()
        );
    }

    @GetMapping("/projects/workload")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<ProjectWorkloadResponse>> projectWorkload() {
        return ResponseEntity.ok(
                projectAnalyticsService.getProjectWorkload()
        );
    }

    @GetMapping("/projects/risks")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<ProjectRiskResponse>> projectRisks() {
        return ResponseEntity.ok(
                projectAnalyticsService.getProjectRisks()
        );
    }

    @GetMapping("/alerts")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<List<AlertResponse>> alerts() {
        return ResponseEntity.ok(
                alertAnalyticsService.getAllAlerts()
        );
    }
}