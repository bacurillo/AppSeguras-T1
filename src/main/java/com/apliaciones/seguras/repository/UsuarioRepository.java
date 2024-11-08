package com.apliaciones.seguras.repository;

import com.apliaciones.seguras.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends GenericRepository<Usuario, Long> {
    Usuario findByUsernameAndPassword(String username, String password);
}