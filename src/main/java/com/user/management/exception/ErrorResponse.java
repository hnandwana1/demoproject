package com.user.management.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Error response structure")
public record ErrorResponse(
        @Schema(description = "HTTP status code", example = "404")
        int status,
        
        @Schema(description = "Error type", example = "User not found")
        String error,
        
        @Schema(description = "Error message", example = "User with ID 1 not found")
        String message,
        
        @Schema(description = "Timestamp when error occurred")
        LocalDateTime timestamp
) {}