package com.apliaciones.seguras.security;


import com.apliaciones.seguras.model.Usuario;
import com.apliaciones.seguras.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UsuarioRepository userRepository; // Interfaz para interactuar con la BD

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String username, String password) {
        Usuario user = userRepository.findByUsernameAndPassword(username, password);//  busco el usuario y contraseña en mi bd
        if (user != null) { // si ecuentro el usuario genero el token
            // Usuario autenticado
            return jwtUtil.generateToken(username);
        }else{
            return "Invalid credentials";
        }
    }
}