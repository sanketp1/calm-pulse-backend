package com.neocortex.services.impl;

import com.neocortex.exceptions.UserAlreadyExistsException;
import com.neocortex.models.User;
import com.neocortex.models.enums.Role;
import com.neocortex.payloads.AuthenticationRequest;
import com.neocortex.payloads.AuthenticationResponse;
import com.neocortex.payloads.RegistrationRequest;
import com.neocortex.repositories.UserRepository;
import com.neocortex.security.jwt.JwtService;
import com.neocortex.services.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService implements IAuthenticationService {

    private  final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;


    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        log.info("Login attempt for email: {}", authenticationRequest.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );

            final var userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
            final String accessToken = jwtService.generateToken(userDetails.getUsername());

            log.info("Login successful for email: {}", authenticationRequest.getEmail());

            return AuthenticationResponse
                    .builder()
                    .accessToken(accessToken)
                    .createdAt(LocalDateTime.now())
                    .build();
        } catch (UsernameNotFoundException ex) {
            log.error("Login failed: User not found for email: {}", authenticationRequest.getEmail());
            throw new UsernameNotFoundException("Invalid credentials");
        } catch (Exception ex) {
            log.error("Login failed for email: {}. Reason: {}", authenticationRequest.getEmail(), ex.getMessage());
            throw new RuntimeException("Invalid credentials");
        }
    }

 @Override
public AuthenticationResponse register(RegistrationRequest registrationRequest) {
    log.info("Registration attempt for email: {}", registrationRequest.getEmail());
    try {
        // Check if the user already exists
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            log.warn("Registration failed: Email already in use: {}", registrationRequest.getEmail());
            throw new UserAlreadyExistsException("Email is already in use");
        }

        // Encode the password
        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());

        // Save the user to the database
        var user = userRepository.save(
                User.builder()
                        .email(registrationRequest.getEmail())
                        .password(encodedPassword)
                        .name(registrationRequest.getName())
                        .phoneNumber(registrationRequest.getPhoneNumber())
                        .address(registrationRequest.getAddress())
                        .avatar(registrationRequest.getAvatarURL())
                        .joinedDate(LocalDateTime.now())
                        .lastUpdated(LocalDateTime.now())
                        .role(Role.USER) // Default role
                        .build()
        );

        // Generate tokens
        final String accessToken = jwtService.generateToken(user.getEmail());
//        final String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        log.info("Registration successful for email: {}", registrationRequest.getEmail());

        return AuthenticationResponse
                .builder()
                .accessToken(accessToken)
//                .refreshToken(refreshToken)
                .createdAt(LocalDateTime.now())
                .build();
    } catch (IllegalArgumentException ex) {
        log.error("Registration failed for email: {}. Reason: {}", registrationRequest.getEmail(), ex.getMessage());
        throw ex;
    } catch (Exception ex) {
        log.error("Unexpected error during registration for email: {}. Reason: {}", registrationRequest.getEmail(), ex.getMessage());
        throw new RuntimeException("Registration failed due to an unexpected error");
    }
}
    @Override
    public void logout(String token) {

    }

    @Override
    public AuthenticationResponse refreshToken(String token) {
        return null;
    }
}
