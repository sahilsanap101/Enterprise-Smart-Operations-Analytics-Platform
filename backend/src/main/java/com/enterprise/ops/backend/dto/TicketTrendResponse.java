package com.enterprise.ops.backend.analytics.dto;

public record TicketTrendResponse(
        String status,
        long count
) {}