package com.apliaciones.seguras.controller;

import com.apliaciones.seguras.model.Usuario;
import com.apliaciones.seguras.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ejIntermedios")
public class ejIntermedios {

    @Autowired
    private UsuarioService usuarioService;

    // 1.- Un sistema de autenticación que compare un nombre de
    //     usuario y una contraseña ingresados, comparándolos con
    //     valores predefinidos en un archivo de texto plano.
    private static Map<String, String> cargarArchivo(String archivo) {
        Map<String, String> usuarios = new HashMap<>();
        // Cargar el archivo desde el directorio de recursos
        try (InputStream inputStream = ejIntermedios.class.getClassLoader().getResourceAsStream(archivo);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String linea;
            // Leer el archivo línea por línea
            while ((linea = br.readLine()) != null) {
                // Separar la línea en usuario y contraseña usando ":"
                String[] partes = linea.split(":"); // Separar nombre de usuario y contraseña
                if (partes.length == 2) {// Me aseguro que tiene usuario y contraseña
                    String usuario = partes[0].trim();
                    String contrasena = partes[1].trim();
                    // Agregar usuario y contraseña al mapa
                    usuarios.put(usuario, contrasena); // Agregar a la lista de usuarios
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones
        }

        return usuarios;
    }

    @GetMapping("/validarUsuario")// Endpoint para validar las credenciales de usuario
    public ResponseEntity<String> validarUsuario(@RequestParam String user, @RequestParam String pass) {
        String response = "";
        // Cargar usuarios desde el archivo
        Map<String, String> usuarios = cargarArchivo("usuarios.txt");
        // Verificar si el usuario existe y la contraseña es correcta
        if (usuarios.containsKey(user) &&
                usuarios.get(user).equals(pass)) {
            response = "Autenticación exitosa. Bienvenido, " + user + "!";
        } else if (!usuarios.containsKey(user)) {// Verificar si el usuario existe
            response = "Autenticación fallida. Nombre de usuario incorrecto.";
        } else {// Verificar si la contraseña es correcta
            response = "Autenticación fallida. Contraseña incorrecta.";
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 2.- Escribe un programa que se conecte a una base de datos y
    //     ejecute una consulta de forma segura usando
    //     PreparedStatement para evitar inyecciones SQL
    // Metodo 1 preparedStatement
    @GetMapping("/usuariosPreparedStatement")
    public List<Usuario> getUsuariosByUsername1(@RequestParam String username) {
        return usuarioService.findByUsername1(username);
    }

    // Metodo 2 JdbcTemplate
    @GetMapping("/usuariosJdbcTemplate")
    public List<Usuario> getUsuariosByUsername2(@RequestParam String username) {
        return usuarioService.findByUsername2(username);
    }
}
