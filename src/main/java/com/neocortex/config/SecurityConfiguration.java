package com.neocortex.config;

import com.neocortex.models.enums.Role;
import com.neocortex.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.neocortex.models.enums.Role.ADMIN;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CalmPulseAuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authRequest) -> {
                        authRequest.requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/v3/api-docs/public",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll();
                    authRequest.requestMatchers("/api/v1/auth/login").permitAll();
                    authRequest.requestMatchers("/api/v1/auth/register").permitAll();
                    authRequest.requestMatchers(HttpMethod.GET, "/api/v1/users").hasRole(ADMIN.name());
                    authRequest.requestMatchers(HttpMethod.DELETE, "/api/v1/users").hasRole(ADMIN.name());
                    authRequest.requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasRole(ADMIN.name());
                    authRequest.anyRequest().authenticated();
                })
                .sessionManagement((session) -> {session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);})
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
