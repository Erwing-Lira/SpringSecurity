package com.elira.springSecurityBasic.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "Spring Security",
                description = "How to use and implement Spring Security from the basic username and password to JWT",
                version = "1.0.0",
                contact = @Contact(
                        name = "Erwing",
                        email = "elira.developer@gmail.com"
                ),
                license = @License(
                        name = "Licence used by Erwing"
                )
        ),
        servers = {
                @Server(
                        description = "DEV Server",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "PROD Server",
                        url = "http://localhost:8081"
                )
        },
        security = @SecurityRequirement(
                name = "Security Token"
        )
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP,
        name = "Security Token",
        description = "Access Token with JWT",
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "Bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
