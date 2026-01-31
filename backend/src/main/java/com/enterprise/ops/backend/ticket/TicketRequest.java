package com.enterprise.ops.backend.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TicketRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private TicketType type;

    @NotNull
    private TicketPriority priority;

    @NotNull
    private Long projectId;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TicketType getType() { return type; }
    public TicketPriority getPriority() { return priority; }
    public Long getProjectId() { return projectId; }
}