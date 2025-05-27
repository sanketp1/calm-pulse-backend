package com.neocortex.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neocortex.exceptions.UserAlreadyExistsException;
import com.neocortex.models.Token;
import com.neocortex.models.User;
import com.neocortex.models.enums.Role;
import com.neocortex.payloads.AuthenticationRequest;
import com.neocortex.payloads.AuthenticationResponse;
import com.neocortex.payloads.RegistrationRequest;
import com.neocortex.repositories.TokenRepository;
import com.neocortex.repositories.UserRepository;
import com.neocortex.security.jwt.JwtService;
import com.neocortex.services.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.neocortex.models.enums.TokenType.BEARER;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService implements IAuthenticationService {

    private  final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
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

            final User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
            user.setLastLogin(LocalDateTime.now());

            final String accessToken = jwtService.generateToken(user.getId().toString());
            final String refreshToken = jwtService.generateRefreshToken(user.getId().toString());

            revokeAllUserTokens(user);
            saveUserToken(user,accessToken);
            userRepository.save(user); // Save the updated user with last login time
            log.info("Login successful for email: {}", authenticationRequest.getEmail());

            return AuthenticationResponse
                    .builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
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
    // Check if the user already exists
    if (userRepository.existsByEmail(registrationRequest.getEmail())) {
        log.warn("Registration failed: Email already in use: {}", registrationRequest.getEmail());
        throw new UserAlreadyExistsException("Email is already in use");
    }

    log.info("Registration attempt for email: {}", registrationRequest.getEmail());

    try {

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
                        .avatarURL(registrationRequest.getAvatarURL())
                        .lastUpdated(LocalDateTime.now())
                        .role(Role.USER) // Default role
                        .build()
        );

        // Generate tokens
        final String accessToken = jwtService.generateToken(user.getId().toString());
        final String refreshToken = jwtService.generateRefreshToken(user.getId().toString());

        saveUserToken(user,accessToken);

        log.info("Registration successful for email: {}", registrationRequest.getEmail());

        return AuthenticationResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
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
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Invalid or missing Authorization header");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid or missing Authorization header");
            return;
        }

        final String refreshToken = authHeader.substring(7);
        String userId;

        try {
            userId = jwtService.extractUserId(refreshToken);
        } catch (Exception ex) {
            log.error("Failed to extract username from refresh token: {}", ex.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid refresh token");
            return;
        }

        if (userId == null) {
            log.warn("Refresh token does not contain a valid user id");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid refresh token");
            return;
        }

        final User user;
        try{
            user = userRepository.findById(UUID.fromString(userId)).orElseThrow();
        }
        catch (UsernameNotFoundException ex){
            log.error("User not found for id: {}", userId);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("User not found");
            return;
        }

        if (!jwtService.isValidToken(userId, refreshToken)) {
            log.warn("Invalid refresh token for user: {}", userId);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid refresh token");
            return;
        }

        try {
            var accessToken = jwtService.generateToken(user.getId().toString());
            revokeAllUserTokens(user);
            saveUserToken(user, accessToken);

            var authResponse = AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            log.info("Refresh token processed successfully for user: {}", userId);
        } catch (Exception ex) {
            log.error("Error while processing refresh token for user: {}. Reason: {}", userId, ex.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to process refresh token");
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
