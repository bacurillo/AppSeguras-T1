package com.apliaciones.seguras.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desactivar la protección contra CSRF (Cross-Site Request Forgery) porque esta protección está pensada para ataques que ocurren en aplicaciones basadas en sesiones y cookies
                                              //se deshabilita para no requerir un token basado en CSRF
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/auth/**").permitAll()  // Permite el acceso a estas rutas auth
                        .requestMatchers("/**").permitAll()  // Permite el acceso público a estas rutas
                        .anyRequest().authenticated() // Exige autenticación para las demás rutas
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Esto garantiza que el filtro se ejecute en cada solicitud entrante y que verifique la validez del token JWT

        return http.build();
    }
}