package com.neocortex.services;

import com.neocortex.payloads.AuthenticationRequest;
import com.neocortex.payloads.AuthenticationResponse;
import com.neocortex.payloads.RegistrationRequest;

/**
 * Service interface for handling authentication-related operations within the application.
 * <p>
 * This interface defines the contract for authentication services, including user login,
 * registration, logout, and token refresh functionalities. Implementations of this interface
 * are responsible for managing authentication workflows and token management.
 * </p>
 * <p>
 * Fields and their purposes:
 * <ul>
 *   <li><b>login(AuthenticationRequest authenticationRequest)</b>: Authenticates a user using provided credentials and returns an authentication response containing tokens.</li>
 *   <li><b>register(RegistrationRequest registrationRequest)</b>: Registers a new user with the provided registration details and returns an authentication response.</li>
 *   <li><b>logout(String token)</b>: Invalidates the provided token, effectively logging out the user.</li>
 *   <li><b>refreshToken(String token)</b>: Generates a new access token using the provided refresh token.</li>
 * </ul>
 * </p>
 */
public interface IAuthenticationService {

    /**
     * Authenticates a user based on the provided authentication request.
     *
     * @param authenticationRequest the {@link AuthenticationRequest} containing user credentials (email and password)
     * @return {@link AuthenticationResponse} containing access and refresh tokens, and creation timestamp
     */
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);

    /**
     * Registers a new user with the provided registration details.
     *
     * @param registrationRequest the {@link RegistrationRequest} containing user registration information
     * @return {@link AuthenticationResponse} containing access and refresh tokens, and creation timestamp
     */
    AuthenticationResponse register(RegistrationRequest registrationRequest);

    /**
     * Logs out the user by invalidating the provided token.
     *
     * @param token the JWT or session token to be invalidated (must not be null)
     */
    void logout(String token);

    /**
     * Refreshes the authentication tokens using the provided refresh token.
     *
     * @param token the refresh token (must be valid and not expired)
     * @return {@link AuthenticationResponse} containing new access and refresh tokens, and creation timestamp
     */
    AuthenticationResponse refreshToken(String token);

}