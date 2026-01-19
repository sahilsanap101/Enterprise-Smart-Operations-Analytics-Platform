package com.enterprise.ops.backend.ticket;

import jakarta.validation.constraints.NotBlank;

public class TicketCommentRequest {

    @NotBlank(message = "Comment cannot be empty")
    private String comment;

    public String getComment() {
        return comment;
    }
}
