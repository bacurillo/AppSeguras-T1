package com.apliaciones.seguras.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {//OncePerRequestFilter, una clase de Spring Security que asegura que el filtro se aplique una vez por cada solicitud HTTP

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)//Este método se ejecuta en cada solicitud y tiene la lógica para verificar el token JWT y configurar la autenticación.
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");// obtiene el encabezado de la solicitud

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {//Validación del formato del token
            String token = authorizationHeader.substring(7);//Se elimina la palabra "Bearer " y se extrae solo el token

            if (jwtUtil.validateToken(token)) {//verifica si el token es válido
                String username = jwtUtil.extractUsername(token);//Si el token es válido, se extrae el nombre de usuario para establecerlo en la autenticación.
                // Crear un objeto Authentication
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()); // Aquí puedes agregar roles si es necesario

                // Establecer el contexto de seguridad con el token de autenticación
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");//Si el token no es válido, se responde con un error 401
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}