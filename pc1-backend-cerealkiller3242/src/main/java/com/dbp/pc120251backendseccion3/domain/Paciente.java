package com.dbp.pc120251backendseccion3.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "pacientes")
public class Paciente {

    @Id
    @Column(name = "numero_historia_clinica", nullable = false, unique = true)
    private String numeroHistoriaClinica;

    @NotBlank
    @Column(nullable = false)
    private String nombres;

    @NotBlank
    @Column(nullable = false)
    private String apellidos;

    @NotNull
    @Past
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_sangre", nullable = false)
    private TipoSangre tipoSangre;

    @ElementCollection
    @CollectionTable(name = "paciente_alergias", joinColumns = @JoinColumn(name = "paciente_id"))
    @Column(name = "alergia")
    private List<String> alergias = new ArrayList<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPaciente estado = EstadoPaciente.ACTIVO;

    @Email
    @Column(unique = true)
    private String correoElectronico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_cabecera_id")
    private Medico medicoCabecera;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consulta> consultas = new ArrayList<>();

    public enum Sexo {
        MASCULINO, FEMENINO, OTRO
    }

    public enum TipoSangre {
        A_POSITIVO("A+"), A_NEGATIVO("A-"),
        B_POSITIVO("B+"), B_NEGATIVO("B-"),
        AB_POSITIVO("AB+"), AB_NEGATIVO("AB-"),
        O_POSITIVO("O+"), O_NEGATIVO("O-");

        private final String simbolo;

        TipoSangre(String simbolo) {
            this.simbolo = simbolo;
        }

        public String getSimbolo() {
            return simbolo;
        }
    }

    public enum EstadoPaciente {
        ACTIVO, INACTIVO, FALLECIDO
    }
}