    package com.imperialnet.el_ceibo.configuration;

    import lombok.RequiredArgsConstructor;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.authentication.AuthenticationProvider;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.web.cors.CorsConfiguration;

    import java.util.Arrays;
    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
    public class SecurityConfig {

        private final JwtAthenticationFilter jwtAthenticationFilter;
        private final AuthenticationProvider authenticationProvider;
        private final JwtCookieAuthenticationFilter jwtCookieAuthenticationFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .cors(cors -> cors.configurationSource(request -> {
                        CorsConfiguration corsConfiguration = new CorsConfiguration();
                        corsConfiguration.setAllowedOrigins(Arrays.asList(
                                "https://elceibo.imperial-net.com", "http://localhost:3000", "http://localhost:3001" // Aceptar solo solicitudes desde el frontend
                        ));
                        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                        corsConfiguration.addAllowedHeader("*"); // Permitir todos los encabezados
                        corsConfiguration.setAllowCredentials(true); // Permitir credenciales (cookies)
                        return corsConfiguration;
                    }))
                    .csrf().disable()
                    .authorizeHttpRequests()
                    .requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authenticationProvider(authenticationProvider)
                    .addFilterBefore(jwtCookieAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(jwtAthenticationFilter, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }
    }
