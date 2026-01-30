package com.enterprise.ops.backend.analytics;

public record AnalyticsResponse(
        long totalTickets,
        long openTickets,
        long closedTickets,
        long slaBreachedTickets,
        double averageResolutionHours
) {}
