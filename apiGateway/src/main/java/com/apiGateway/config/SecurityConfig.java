package com.apiGateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.apiGateway.security.AuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final AuthenticationFilter authenticationFilter;

    public SecurityConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf(csrf -> csrf.disable()) // Disable CSRF
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/auth/register","/auth/token","/auth/valid","/apputil/**","/swagger-ui/**", "/v3/api-docs/**").permitAll() // Allow public access to auth endpoints
                        
                        .pathMatchers("/auth/**","/orders/**","/catalog/**","/payments/**","/store/**","/usercart/**").authenticated() // Require authentication for other endpoints
                        .anyExchange().permitAll()   
                )
                .addFilterBefore(authenticationFilter,  SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
   
}
	
