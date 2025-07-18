package com.user.management.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "User data transfer object")
public record UserDTO(
        @Schema(description = "User ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890")
        UUID id,
        
        @Schema(description = "User name", example = "John Doe")
        String name,
        
        @Schema(description = "User address", example = "123 Main St, City, State")
        String address
) {}
