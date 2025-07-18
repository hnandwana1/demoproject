package com.user.management.controller;

import com.user.management.model.UserDTO;
import com.user.management.request.UserRequest;
import com.user.management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management", description = "Operations related to user management")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users with pagination", description = "Retrieve a paginated list of all users")
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @PageableDefault(size = 10, sort = "id") 
            @Parameter(description = "Pagination parameters") Pageable pageable) {
        log.info("Fetching users with pagination: {}", pageable);
        Page<UserDTO> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a user by their ID")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable 
            @Parameter(description = "User ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890") UUID id) {
        log.info("Fetching user with ID: {}", id);
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user in the system")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<UserDTO> createUser(
            @Valid @RequestBody UserRequest userRequest) {
        log.info("Creating new user: {}", userRequest.name());
        UserDTO createdUser = userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an existing user")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable 
            @Parameter(description = "User ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890") UUID id,
            @Valid @RequestBody UserRequest userRequest) {
        log.info("Updating user with ID: {}", id);
        UserDTO updatedUser = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user by their ID")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Void> deleteUser(
            @PathVariable 
            @Parameter(description = "User ID", example = "a1b2c3d4-e5f6-7890-abcd-ef1234567890") UUID id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
