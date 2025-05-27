package com.neocortex.controllers;

import com.neocortex.payloads.AuthenticationRequest;
import com.neocortex.payloads.AuthenticationResponse;
import com.neocortex.payloads.RegistrationRequest;
import com.neocortex.services.impl.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * Controller for handling authentication-related operations such as login, registration, logout, and token refresh.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Handles user login requests.
     *
     * @param authenticationRequest the request payload containing user credentials
     * @return a ResponseEntity containing the authentication response with a JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest) {
        log.info("Login request received for user: {}", authenticationRequest.getEmail());
        final AuthenticationResponse authenticationResponse = authenticationService.login(authenticationRequest);
        log.info("Login successful for user: {}", authenticationRequest.getEmail());
        return ResponseEntity.ok(authenticationResponse);
    }

    /**
     * Handles user registration requests.
     *
     * @param registrationRequest the request payload containing user registration details
     * @return a ResponseEntity containing the authentication response with a JWT token
     */
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest registrationRequest) {
        log.info("Registration request received for user: {}", registrationRequest.getEmail());
        final AuthenticationResponse authenticationResponse = authenticationService.register(registrationRequest);
        log.info("Registration successful for user: {}", registrationRequest.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponse);
    }

    /**
     * Handles user logout requests.
     * Currently, this method does not perform any specific action.
     */
    @PostMapping("/logout")
    public void logout() {
        log.info("Logout request received.");
        // Implement logout logic if needed
        log.info("Logout process completed.");
    }

    /**
     * Handles token refresh requests.
     *
     * @param httpServletRequest  the HTTP request containing the refresh token
     * @param httpServletResponse the HTTP response to send the new token
     */
    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        log.info("Token refresh request received.");
        authenticationService.refreshToken(httpServletRequest,httpServletResponse);
        log.info("Token refresh process completed.");
    }
}