package com.enterprise.ops.backend.common;

import java.time.LocalDateTime;

public record ErrorResponse(
        String error,
        LocalDateTime timestamp
) {}