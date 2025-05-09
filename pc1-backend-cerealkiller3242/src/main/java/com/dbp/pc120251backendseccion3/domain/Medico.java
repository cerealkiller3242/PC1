package com.dbp.pc120251backendseccion3.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "medicos")
public class Medico {

    @Id
    @Column(name = "codigo_colegiatura", nullable = false, unique = true)
    private String codigoColegiatura;

    @NotBlank
    @Column(nullable = false)
    private String nombres;

    @NotBlank
    @Column(nullable = false)
    private String apellidos;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especialidad especialidad;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMedico estado = EstadoMedico.ACTIVO;

    @Email
    @NotBlank
    @Column(name = "correo_institucional", nullable = false, unique = true)
    private String correoInstitucional;

    @OneToMany(mappedBy = "medicoCabecera", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paciente> pacientes = new ArrayList<>();

    @OneToMany(mappedBy = "medico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consulta> consultas = new ArrayList<>();

    public enum Especialidad {
        MEDICINA_GENERAL, CARDIOLOGIA, DERMATOLOGIA, PEDIATRIA, NEUROLOGIA, GINECOLOGIA, TRAUMATOLOGIA
    }

    public enum EstadoMedico {
        ACTIVO, LICENCIA, RETIRADO
    }
}
