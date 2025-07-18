package com.user.management.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UserRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
        String name,
        
        @NotBlank(message = "Address is required")
        @Size(min = 5, max = 200, message = "Address must be between 5 and 200 characters")
        String address
) {}
