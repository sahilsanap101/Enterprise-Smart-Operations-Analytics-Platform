package com.enterprise.ops.backend.analytics.dto;

public record ProjectRiskResponse(
        Long projectId,
        String projectName,
        String riskLevel,
        String reason
) {}