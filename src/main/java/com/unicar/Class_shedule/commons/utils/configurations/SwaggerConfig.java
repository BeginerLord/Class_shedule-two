package com.unicar.Class_shedule.commons.utils.configurations;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
@Configuration
 @OpenAPIDefinition(
        info = @Info(
                title = "Horario de Clase - Programador Crack",
                description = "La aplicación proporciona los horarios de clase de manera eficiente y organizada.",
                termsOfService = "www.programadorcrack.com/terminos_y_condiciones",
                version = "1.0.0",
                contact = @Contact(
                        name = "Equipo Programador Crack",
                        url = "https://programadorcrack.com",
                        email = "contacto@programadorcrack.com"
                ),
                license = @License(
                        name = "Licencia de Uso para Programador Crack",
                        url = "www.programadorcrack.com/licencia"
                )
        ),
        servers = {
                @Server(
                        description = "SERVIDOR DE DESARROLLO",
                        url = "http://localhost:8080"
                )
        },
        security = @SecurityRequirement(
                name = "Token de Seguridad"
        )
)
@SecurityScheme(
        name = "Token de Seguridad",
        description = "Token de Acceso para la API de Horarios de Clase",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
