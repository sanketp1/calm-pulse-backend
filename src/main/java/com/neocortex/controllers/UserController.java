package com.neocortex.controllers;

import com.neocortex.payloads.PaginatedResponse;
import com.neocortex.payloads.user.UpdateUserRequest;
import com.neocortex.payloads.user.UserResponse;
import com.neocortex.services.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private final IUserService userService;

    @Operation(
        summary = "Get current authenticated user",
        description = "Returns the details of the currently authenticated user."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Current user details retrieved",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
        log.info("Fetching current user");
        UserResponse userResponse = userService.getCurrentUser(authentication);
        log.info("Current user fetched successfully: {}", userResponse);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(
        summary = "Get user by ID",
        description = "Fetch a user by their unique identifier."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(
            @Parameter(description = "UUID of the user to fetch", required = true)
            @PathVariable("id") UUID id) {
        log.info("Fetching user with ID: {}", id);
        UserResponse userResponse = userService.getUserById(id);
        log.info("User fetched successfully: {}", userResponse);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(
        summary = "Get all users (admin only)",
        description = "Returns a paginated list of all users. Requires admin privileges."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Users retrieved",
            content = @Content(schema = @Schema(implementation = PaginatedResponse.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<PaginatedResponse<UserResponse>> getAllUsers(
            @Parameter(description = "Cursor for pagination") @RequestParam(required = false) String cursor,
            @Parameter(description = "Page size", example = "10") @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching all users with cursor: {} and size: {}", cursor, size);
        PaginatedResponse<UserResponse> paginatedResponse = userService.getAllUsers(cursor, size);
        log.info("Users fetched successfully. Total users: {}",
                 paginatedResponse.getData() != null ? paginatedResponse.getData().size() : 0);
        return ResponseEntity.ok(paginatedResponse);
    }

    @Operation(
        summary = "Update user by ID",
        description = "Update the details of a user by their unique identifier."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User updated",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PutMapping("/{id}")
    @PreAuthorize("#id.toString() == authentication.principal.id or hasAuthority('admin:update')")
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "UUID of the user to update", required = true)
            @PathVariable("id") UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated user details",
                required = true,
                content = @Content(schema = @Schema(implementation = UpdateUserRequest.class))
            )
            @RequestBody UpdateUserRequest updateUserRequest) {
        log.info("Updating user with ID: {}", id);
        UserResponse updatedUser = userService.updateUser(id, updateUserRequest);
        log.info("User updated successfully: {}", updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(
        summary = "Delete user by ID (admin only)",
        description = "Delete a user by their unique identifier. Requires admin privileges."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "User deleted"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "UUID of the user to delete", required = true)
            @PathVariable("id") UUID id) {
        log.info("Deleting user with ID: {}", id);
        userService.deleteUser(id);
        log.info("User deleted successfully: {}", id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Delete multiple users (admin only)",
        description = "Delete multiple users by their UUIDs. Requires admin privileges."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Users deleted"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "403", description = "Forbidden"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Void> deleteMultipleUsers(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "List of user UUIDs to delete",
                required = true,
                content = @Content(schema = @Schema(implementation = UUID.class))
            )
            @RequestBody List<UUID> ids) {
        log.info("Deleting all users");
        userService.deleteMultipleUsers(ids);
        log.info("All users deleted successfully");
        return ResponseEntity.noContent().build();
    }
}