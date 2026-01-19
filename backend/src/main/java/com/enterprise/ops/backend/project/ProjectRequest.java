package com.enterprise.ops.backend.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ProjectRequest {

    @NotBlank(message = "Project name is required")
    private String name;

    private String description;

    @NotNull(message = "Start date is required")
    @Pattern(
        regexp = "\\d{4}-\\d{2}-\\d{2}",
        message = "Start date must be in YYYY-MM-DD format"
    )
    private String startDate;

    @NotNull(message = "End date is required")
    @Pattern(
        regexp = "\\d{4}-\\d{2}-\\d{2}",
        message = "End date must be in YYYY-MM-DD format"
    )
    private String endDate;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
