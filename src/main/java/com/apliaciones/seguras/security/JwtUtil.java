package com.apliaciones.seguras.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "APPSEGURAHS256123455431233223121123";
    private static final long EXPIRATION_TIME = 86400000; // 24 horas en milisegundos

    private Key getSigningKey() {//devuelve la clave de firma (Key) necesaria para firmar y validar los toke
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {//generar token
        return Jwts.builder()
                .setSubject(username)//Establece el nombre de usuario como el "subject" (sujeto) del token, para asociar el token a un usuario específico
                .setIssuedAt(new Date())//fecha de emisión
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))//Define la fecha de expiración del token sumando el tiempo de expiración al momento actual.
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)//Firma el token utilizando la clave de firma obtenida en getSigningKey() y el algoritmo HS256.
                .compact(); //Genera el token en String
    }

    public boolean validateToken(String token) {//Este método verifica si el token JWT proporcionado es válido.
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);//analizar el token con la clave de firma.
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String extractUsername(String token) {//extrae el nombre de usuario del token
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}