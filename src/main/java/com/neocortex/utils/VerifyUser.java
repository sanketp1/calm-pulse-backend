package com.neocortex.utils;

import com.neocortex.exceptions.UserMismatchException;
import com.neocortex.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VerifyUser {

    private final JwtService jwtService;
public void isSelf(UUID requestedUserId) {
    if (requestedUserId == null) {
        throw new UserMismatchException("Requested user ID is null.");
    }

    String token = extractTokenFromRequest();
    if (token == null) {
        throw new UserMismatchException("Authorization token is missing.");
    }

    try {
        String tokenUserId = jwtService.extractUserId(token); // returns string
        if (!requestedUserId.toString().equals(tokenUserId)) {
            throw new UserMismatchException("The requested user ID does not match the token user ID.");
        }
    } catch (UserMismatchException e) {
        throw e; // Rethrow the original exception
    } catch (Exception e) {
        throw new UserMismatchException("Invalid token provided.");
    }
}

private String extractTokenFromRequest() {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attrs == null) {
        throw new UserMismatchException("Unable to retrieve the request attributes.");
    }

    HttpServletRequest request = attrs.getRequest();
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
        return authHeader.substring(7);
    }

    throw new UserMismatchException("Authorization header is missing or malformed.");
}
}
