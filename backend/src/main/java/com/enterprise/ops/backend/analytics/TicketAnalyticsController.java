package com.enterprise.ops.backend.analytics;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/analytics/tickets")
public class TicketAnalyticsController {

    private final TicketAnalyticsService analyticsService;

    public TicketAnalyticsController(
            TicketAnalyticsService analyticsService
    ) {
        this.analyticsService = analyticsService;
    }

    // ADMIN & MANAGER only
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<AnalyticsResponse> getTicketAnalytics() {
        return ResponseEntity.ok(
                analyticsService.getTicketAnalytics()
        );
    }
}
