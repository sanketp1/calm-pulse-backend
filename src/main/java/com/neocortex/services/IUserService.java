package com.neocortex.services;


import com.neocortex.payloads.PaginatedResponse;
import com.neocortex.payloads.user.UpdateUserRequest;
import com.neocortex.payloads.user.UserResponse;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing user-related operations within the application.
 * <p>
 * This interface defines methods for retrieving, updating, and deleting user information.
 * It is intended to be implemented by service classes that handle user management logic.
 * <p>
 * <b>Note:</b> Some methods are not restricted to admin users and may be accessible to regular users,
 * such as {@link #getUserById(UUID)} and {@link #updateUser(UUID, UpdateUserRequest)}.
 * Methods like {@link #getAllUsers(String,Integer)}, {@link #deleteUser(UUID)}, and {@link #deleteMultipleUsers(List)} are typically admin-only.
 * </p>
 * <p>
 * <b>Fields and their purposes:</b>
 * <ul>
 *   <li><b>getUserById(UUID id):</b> Retrieves a user's details by their unique identifier. (Not admin-only)</li>
 *   <li><b>getUserByEmail(String email):</b> Retrieves a user's details by their email address. (Not admin-only)</li>
 *   <li><b>getAllUsers():</b> Returns a list of all users in the system. (Admin-only)</li>
 *   <li><b>updateUser(UUID id, UpdateUserRequest updateUserRequest):</b> Updates user information for the specified user. (Not admin-only)</li>
 *   <li><b>deleteUser(UUID id):</b> Deletes a user by their unique identifier. (Admin-only)</li>
 *   <li><b>deleteMultipleUsers(List&lt;UUID&gt; ids):</b> Deletes multiple users by their unique identifiers. (Admin-only)</li>
 * </ul>
 */
public interface IUserService {

    /**
     * Retrieves details of the currently authenticated user.
     *
     * @param authentication the {@link Authentication} object containing the current user's authentication details
     * @return {@link UserResponse} containing the authenticated user's details
     */
    UserResponse getCurrentUser(Authentication authentication);

    /**
     * Retrieves a user's details by their unique identifier.
     *
     * @param id the {@link UUID} of the user to retrieve
     * @return {@link UserResponse} containing user details
     */
    UserResponse getUserById(UUID id);

    /**
     * Retrieves a paginated list of all users in the system.
     * <p>
     * <b>Admin-only operation.</b>
     * </p>
     *
     * @param cursorId the cursor string for pagination
     * @param size      the number of users to retrieve per page
     * @return {@link PaginatedResponse} containing a list of {@link UserResponse} objects
     */
    PaginatedResponse<UserResponse> getAllUsers(String cursorId, Integer size);

    /**
     * Updates user information for the specified user.
     *
     * @param id                the {@link UUID} of the user to update
     * @param updateUserRequest the {@link UpdateUserRequest} containing updated user information
     * @return {@link UserResponse} with updated user details
     */
    UserResponse updateUser(UUID id, UpdateUserRequest updateUserRequest);


    /**
     * Changes the password for the specified user.
     * <p>
     * This method allows updating the password of a user identified by their unique identifier.
     * It is typically used for account recovery or administrative purposes.
     * </p>
     *
     * @param id          the {@link UUID} of the user whose password is to be changed
     * @param newPassword the new password to set for the user
     */
    void changePassword(UUID id, String newPassword);

    /**
     * Deletes a user by their unique identifier.
     * <p>
     * <b>Admin-only operation.</b>
     * </p>
     *
     * @param id the {@link UUID} of the user to delete
     */
    void deleteUser(UUID id);

    /**
     * Deletes multiple users by their unique identifiers.
     * <p>
     * <b>Admin-only operation.</b>
     * </p>
     *
     * @param ids list of {@link UUID} values representing users to delete
     */
    void deleteMultipleUsers(List<UUID> ids);
}