package com.neocortex.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Calm Pulse API")
                        .description("Calm Pulse API provides a set of endpoints for managing user data, journals, reminders, and mood tracking.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Calm Pulse Team")
                                .email("support@calmpulse.com")
                                .url("https://calmpulse.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .components(new Components()
                        .addSecuritySchemes("jwt", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/login", "/api/register")
                .build();
    }

    @Bean
    public GroupedOpenApi protectedApi() {
        return GroupedOpenApi.builder()
                .group("protected")
                .pathsToMatch("/api/**")
                .addOpenApiCustomizer(openApi -> openApi.getPaths().values().stream()
                        .filter(pathItem -> !pathItem.readOperations().isEmpty())
                       .forEach(pathItem -> pathItem.readOperations().forEach(operation -> operation.addSecurityItem(new SecurityRequirement().addList("jwt")))))
                .build();
    }
}