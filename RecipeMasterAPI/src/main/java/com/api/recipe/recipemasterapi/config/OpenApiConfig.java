package com.api.recipe.recipemasterapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "RecipeMaster API",
                description = "A comprehensive Spring Boot REST API for managing recipes with ingredients",
                version = "1.0.0",
                contact = @Contact(
                        name = "RecipeMaster Team",
                        email = "support@recipemaster.com"
                ),
                license = @License(
                        name = "MIT",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local development server"),
                @Server(url = "https://recipe-api-recipemaster.apps.openshift.com", description = "Production server")
        }
)
public class OpenApiConfig {
}

