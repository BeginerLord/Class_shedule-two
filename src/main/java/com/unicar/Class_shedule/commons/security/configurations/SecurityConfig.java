package com.unicar.Class_shedule.commons.security.configurations;

import com.unicar.Class_shedule.commons.utils.constants.EndpointsConstants;
import com.unicar.Class_shedule.commons.security.configurations.filter.JwtTokenValidator;
import com.unicar.Class_shedule.commons.security.persistencie.entities.RoleEnum;
import com.unicar.Class_shedule.commons.security.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        DefaultSecurityFilterChain build = http
                // Deshabilita la protección CSRF (Cross-Site Request Forgery), ya que la aplicación es stateless
                .csrf(AbstractHttpConfigurer::disable)

                // Configura la gestión de sesiones para que sea stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configura las reglas de autorización para las solicitudes HTTP
                .authorizeHttpRequests(httpRequests -> {
                    // Permitir acceso libre a la interfaz de Swagger UI
                    httpRequests.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();

                    // Endpoints públicos accesibles sin autenticación
                    httpRequests.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_LOGIN).permitAll();

                    // Endpoints privados que requieren autenticación y autorización específica
                    httpRequests.requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_SIGNUP)
                            .hasAuthority(RoleEnum.ADMIN.getRoleName());

                    httpRequests
                            .requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_TEACHER)
                            .hasAnyAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.DELETE, EndpointsConstants.ENDPOINT_TEACHER + "/{dni}")
                            .hasAnyAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_TEACHER + "/{dni}")
                            .hasAnyAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.GET, EndpointsConstants.ENDPOINT_TEACHER)
                            .hasAnyAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.GET, EndpointsConstants.ENDPOINT_TEACHER + "/{dni}")
                            .hasAnyAuthority("ROLE_ADMIN");
                    httpRequests
                            .requestMatchers(HttpMethod.POST, EndpointsConstants.ENDPOINT_COURSES)
                            .hasAnyAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.DELETE, EndpointsConstants.ENDPOINT_COURSES+ "/{name}")
                            .hasAnyAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.PUT, EndpointsConstants.ENDPOINT_COURSES+ "/{name}")
                            .hasAnyAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.GET, EndpointsConstants.ENDPOINT_COURSES)
                            .hasAnyAuthority("ROLE_ADMIN")
                            .requestMatchers(HttpMethod.GET, EndpointsConstants.ENDPOINT_COURSES + "/{name}")
                            .hasAnyAuthority("ROLE_ADMIN");

                    // Deniega todas las demás solicitudes que no coincidan con las reglas anteriores
                    httpRequests.anyRequest().denyAll();
                })

                // Agrega un filtro personalizado para validar los tokens JWT
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)

                // Construye la cadena de filtros configurada
                .build();
        return build;
    }
}