package com.enterprise.ops.backend.common;

import java.time.LocalDateTime;

public record ApiError(
        String message,
        String path,
        LocalDateTime timestamp
) {
    public static ApiError of(String message, String path) {
        return new ApiError(message, path, LocalDateTime.now());
    }
}