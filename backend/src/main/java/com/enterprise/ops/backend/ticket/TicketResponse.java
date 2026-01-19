package com.enterprise.ops.backend.ticket;

import java.time.LocalDateTime;

public record TicketResponse(
        Long id,
        String title,
        String priority,
        String status,
        String projectName,
        String createdBy,
        String assignedTo,
        LocalDateTime slaDeadline
) {}
