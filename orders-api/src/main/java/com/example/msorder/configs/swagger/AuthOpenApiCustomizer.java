package com.example.msorder.configs.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.inject.Named;
import org.springdoc.core.customizers.OpenApiCustomizer;

@Named
public class AuthOpenApiCustomizer implements OpenApiCustomizer {
    @Override
    public void customise(OpenAPI openApi) {
        var securitySchemeName = "bearerAuth";
        openApi.getComponents()
                .addSecuritySchemes(
                        securitySchemeName,
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                );
        openApi.addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
    }
}
