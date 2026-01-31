package com.enterprise.ops.backend.analytics.dto;

public record AlertResponse(
        String alertType,
        String message
) {}