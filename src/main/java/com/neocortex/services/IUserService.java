package com.neocortex.services;


import com.neocortex.payloads.UpdateUserRequest;
import com.neocortex.payloads.UserResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing user-related operations within the application.
 * <p>
 * This interface defines methods for retrieving, updating, and deleting user information.
 * It is intended to be implemented by service classes that handle user management logic.
 * <p>
 * <b>Note:</b> Some methods are not restricted to admin users and may be accessible to regular users,
 * such as {@link #getUserById(UUID)}, {@link #getUserByEmail(String)}, and {@link #updateUser(UUID, UpdateUserRequest)}.
 * Methods like {@link #getAllUsers()}, {@link #deleteUser(UUID)}, and {@link #deleteMultipleUsers(List)} are typically admin-only.
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
     * Retrieves a user's details by their unique identifier.
     *
     * @param id the {@link UUID} of the user to retrieve
     * @return {@link UserResponse} containing user details
     */
    UserResponse getUserById(UUID id);

    /**
     * Retrieves a user's details by their email address.
     *
     * @param email the email address of the user to retrieve
     * @return {@link UserResponse} containing user details
     */
    UserResponse getUserByEmail(String email);

    /**
     * Returns a list of all users in the system.
     * <p>
     * <b>Admin-only operation.</b>
     * </p>
     *
     * @return list of {@link UserResponse} objects for all users
     */
    List<UserResponse> getAllUsers();

    /**
     * Updates user information for the specified user.
     *
     * @param id                the {@link UUID} of the user to update
     * @param updateUserRequest the {@link UpdateUserRequest} containing updated user information
     * @return {@link UserResponse} with updated user details
     */
    UserResponse updateUser(UUID id, UpdateUserRequest updateUserRequest);

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