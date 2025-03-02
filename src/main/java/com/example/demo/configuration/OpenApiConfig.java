package com.example.demo.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(contact = @io.swagger.v3.oas.annotations.info.Contact(name = "MyApp"), description = "MyApp", title = "MyApp", version = "1.0.0", license = @io.swagger.v3.oas.annotations.info.License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html"), termsOfService = "http://swagger.io/terms/"

), servers = {
                @io.swagger.v3.oas.annotations.servers.Server(description = "Localhost", url = "http://localhost:8080")
})

@SecurityScheme(name = "Authorization", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")

@Configuration
public class OpenApiConfig {
}
