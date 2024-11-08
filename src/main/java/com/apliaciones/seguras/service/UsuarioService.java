package com.apliaciones.seguras.service;


import com.apliaciones.seguras.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UsuarioService {

    private final DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UsuarioService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    // Método 1 PreparedStatement
    // PreparedStatement permite parametrizar la consulta, lo cual ayuda a prevenir ataques de
    // inyección SQL y mejora el rendimiento de las consultas repetitivas.
    public List<Usuario> findByUsername1(String username) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE username = ?";  // Consulta parametrizada

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // E
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setCodUsuario(resultSet.getLong("cod_Usuario"));
                    usuario.setUsername(resultSet.getString("username"));
                    usuario.setCorreo(resultSet.getString("correo"));
                    usuario.setFechaRegistro(resultSet.getTimestamp("fecha_Registro"));
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    // Metodo 2 JdbcTemplate
    // JdbcTemplate es una clase de Spring que facilita la interacción con la base de datos.
    // Internamente, utiliza PreparedStatement para prevenir inyecciones SQL.
    public List<Usuario> findByUsername2(String username) {
        String sql = "SELECT * FROM usuario WHERE username = ?";
        return jdbcTemplate.query(sql, new Object[]{username}, new UsuarioRowMapper());
    }

    private static class UsuarioRowMapper implements RowMapper<Usuario> {
        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            Usuario usuario = new Usuario();
            usuario.setCodUsuario(rs.getLong("cod_Usuario"));
            usuario.setUsername(rs.getString("username"));
            usuario.setPassword(rs.getString("password"));
            usuario.setCorreo(rs.getString("correo"));
            usuario.setFechaRegistro(rs.getTimestamp("fecha_Registro"));
            return usuario;
        }
    }
}