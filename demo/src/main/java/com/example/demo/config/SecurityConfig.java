package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .anyRequest().permitAll() // Permitir acceso a todas las rutas sin autenticación
            )
            .csrf().disable() // Desactivar CSRF para simplificar las pruebas (no recomendado para producción)
            .logout(logout -> 
                logout
                    .logoutUrl("/logout") // URL para cerrar sesión
                    .logoutSuccessUrl("/") // Redirige a la página de inicio después de cerrar sesión
                    .permitAll()
            );

        return http.build();
    }
}
