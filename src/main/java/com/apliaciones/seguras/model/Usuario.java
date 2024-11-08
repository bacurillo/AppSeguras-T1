package com.apliaciones.seguras.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long codUsuario;

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private String correo;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;

    @PrePersist
    protected void onCreate() {
        fechaRegistro = new Date(); // Asigna la fecha y hora actuales
    }
}
