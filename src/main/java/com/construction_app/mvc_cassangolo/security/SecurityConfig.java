package com.construction_app.mvc_cassangolo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                // Recursos abiertos
                .requestMatchers("/login", "/css/**", "/img/**", "/js/**").permitAll()

                // Rutas solo para ADMIN
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // Rutas para VENDEDOR y ADMIN
                .requestMatchers(
                        "/", 
                        "/etapa-2",
                        "/cotizaciones/**",
                        "/cotizar/**"
                ).hasAnyRole("ADMIN", "VENDEDOR")

                // Todo lo demás requiere login
                .anyRequest().authenticated()
            )

            // Login personalizado
            .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )

            // Logout
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )

            // Vincular nuestro userDetailsService
            .userDetailsService(customUserDetailsService);

        return http.build();
    }
}
