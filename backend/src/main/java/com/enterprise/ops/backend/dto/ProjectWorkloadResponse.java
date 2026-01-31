package com.enterprise.ops.backend.analytics.dto;

public record ProjectWorkloadResponse(
        Long projectId,
        String projectName,
        int totalAllocation,
        String workloadStatus
) {}