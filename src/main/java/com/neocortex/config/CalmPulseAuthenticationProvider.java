package com.neocortex.config;

import com.neocortex.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CalmPulseAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


  /**
 * Performs authentication with the provided {@link Authentication} object.
 *
 * @param authentication the authentication request object
 * @return a fully authenticated object including credentials
 * @throws AuthenticationException if authentication fails
 */
@Override
public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    try {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        var userOptional = userRepository.findByEmail(username);
        if (userOptional.isEmpty()) {
            throw new org.springframework.security.authentication.BadCredentialsException("User not found");
        }

        var user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new org.springframework.security.authentication.BadCredentialsException("Invalid credentials");
        }

        // You may want to return a custom Authentication implementation here
        return new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
    } catch (org.springframework.security.core.AuthenticationException ex) {
        throw ex;
    } catch (Exception ex) {
        throw new org.springframework.security.authentication.InternalAuthenticationServiceException("Authentication failed", ex);
    }
}
    /**
     * Indicates whether this provider supports the indicated {@link Authentication} object.
     *
     * @param authentication the class of the authentication object
     * @return true if supported, false otherwise
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return org.springframework.security.authentication.UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
