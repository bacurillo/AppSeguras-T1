package com.apliaciones.seguras.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/ejBasicos")
public class ejBasicos {

    //Defino las expresiones regulares utilizando la clase Pattern para un manejo mas eficiente de las expresiones
    private static final Pattern PATTERN_NUMERICO = Pattern.compile("^[0-9]+$");
    private static final Pattern PATTERN_MINUSCULAS = Pattern.compile("^[a-z]+$");
    private static final Pattern PATTERN_MAYUSCULAS = Pattern.compile("^[A-Z]+$");
    private static final Pattern PATTERN_LET_O_NUM = Pattern.compile("^[a-zA-Z0-9]+$");
    private static final Pattern PATTERN_LET_Y_NUM = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]+$");

    // 1.- Que lea solo números
    @GetMapping("/validarNumeros")// Definimos un endpoint
    public ResponseEntity<String> validar_numeros(@RequestParam String text) {
        String response = "";
        if (text == null || text.isEmpty()) {// Verificamos si el texto es nulo o está vacío.
            response = "Debe ingresar un texto para ser validado...";
        } else {
            // Si el texto no es nulo ni vacío, se procede a validar su contenido.
            // Utiliza el patrón de expresión regular para verificar si el texto contiene solo números.
            // Establecemos un mensaje segun el cumplimiento de la expresion regular
            if (PATTERN_NUMERICO.matcher(text).matches()) {
                response = "TEXTO VALIDO: El texto que ingreso contiene solo caracteres numericos...";
            } else {
                response = "TEXTO INVALIDO: El texto que ingreso debe contener solo caracteres numericos...";
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 2.- Que lea solo letras minúsculas.
    @GetMapping("/validarMinusculas")// Definimos un endpoint
    public ResponseEntity<String> validar_minusculas(@RequestParam String text) {
        String response = "";
        if (text == null || text.isEmpty()) {// Verificamos si el texto es nulo o está vacío.
            response = "Debe ingresar un texto para ser validado...";
        } else {
            // Si el texto no es nulo ni vacío, se procede a validar su contenido.
            // Utiliza el patrón de expresión regular para verificar si el texto contiene solo letras minusculas.
            // Establecemos un mensaje segun el cumplimiento de la expresion regular
            if (PATTERN_MINUSCULAS.matcher(text).matches()) {
                response = "TEXTO VALIDO: Todos los caracteres estan en minusculas...";
            } else {
                response = "TEXTO INVALIDO: El texto contiene mayusculas o caracteres no alfabeticos...";
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 3.- Que lea solo letras mayúsculas.
    @GetMapping("/validarMayusculas")// Definimos un endpoint
    public ResponseEntity<String> validar_mayusculas(@RequestParam String text) {
        String response = "";
        // Si el texto no es nulo ni vacío, se procede a validar su contenido.
        // Utiliza el patrón de expresión regular para verificar si el texto contiene solo mayusculas.
        // Establecemos un mensaje segun el cumplimiento de la expresion regular
        if (text == null || text.isEmpty()) {// Verificamos si el texto es nulo o está vacío.
            response = "Debe ingresar un texto para ser validado...";
        } else {
            if (PATTERN_MAYUSCULAS.matcher(text).matches()) {
                response = "TEXTO VALIDO: El texto que ingreso contiene solo mayusculas...";
            } else {
                response = "TEXTO INVALIDO: El texto que ingreso contiene minusculas o caracteres no alfabeticos...";
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 4.- Que lea letras o números.
    @GetMapping("/validar_let_o_num")// Definimos un endpoint
    public ResponseEntity<String> validar_let_o_num(@RequestParam String text) {
        String response = "";
        // Si el texto no es nulo ni vacío, se procede a validar su contenido.
        // Utiliza el patrón de expresión regular para verificar si el texto contiene letras o numeros.
        // Establecemos un mensaje segun el cumplimiento de la expresion regular
        if (text == null || text.isEmpty()) {// Verificamos si el texto es nulo o está vacío.
            response = "Debe ingresar un texto para ser validado...";
        } else {
            if (PATTERN_LET_O_NUM.matcher(text).matches()) {
                response = "TEXTO VALIDO: El texto que ingreso es correcto...";
            } else {
                response = "TEXTO INVALIDO: El texto contiene caracteres especiales...";
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 5.- Que lea letras y números (La cadena contiene al menos una letra y al menos un número)
    @GetMapping("/validar_let_y_num")// Definimos un endpoint
    public ResponseEntity<String> validar_let_y_num(@RequestParam String text) {
        String response = "";
        if (text == null || text.isEmpty()) {// Verificamos si el texto es nulo o está vacío.
            response = "Debe ingresar un texto para ser validado...";
        } else {
            // Si el texto no es nulo ni vacío, se procede a validar su contenido.
            // Utiliza el patrón de expresión regular para verificar si el texto contiene al menos una letra y un numero.
            // Establecemos un mensaje segun el cumplimiento de la expresion regular
            if (PATTERN_LET_Y_NUM.matcher(text).matches()) {
                response = "TEXTO VALIDO: El texto que ingreso contiene al menos una letra y un número...";
            } else {
                response = "TEXTO INVALIDO: El texto debe contener al menos una letra y un numero, y no debe tener caracteres especiales...";
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
