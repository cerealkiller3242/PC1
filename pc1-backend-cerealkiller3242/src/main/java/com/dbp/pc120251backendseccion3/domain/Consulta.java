package com.dbp.pc120251backendseccion3.domain;


import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @NotNull
    @Column(nullable = false, length = 1000)
    private String motivo;

    @Column(length = 4000)
    private String diagnostico;

    @Column(length = 4000)
    private String tratamiento;

    @Column(length = 1000)
    private String observaciones;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoConsulta estado = EstadoConsulta.PROGRAMADA;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    // Enums
    public enum EstadoConsulta {
        PROGRAMADA, EN_CURSO, COMPLETADA, CANCELADA, REPROGRAMADA
    }


}

