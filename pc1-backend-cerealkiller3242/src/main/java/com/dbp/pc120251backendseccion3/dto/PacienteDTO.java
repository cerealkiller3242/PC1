package com.dbp.pc120251backendseccion3.dto;

import com.dbp.pc120251backendseccion3.domain.Paciente;

import java.time.LocalDate;
import java.util.List;

public record PacienteDTO(
        String numeroHistoriaClinica,
        String nombres,
        String apellidos,
        LocalDate fechaNacimiento,
        Paciente.Sexo sexo,
        Paciente.TipoSangre tipoSangre,
        List<String> alergias,
        String correoElectronico) {}


