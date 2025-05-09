package com.dbp.pc120251backendseccion3.dto;

import java.time.LocalDateTime;

public record ConsultaDTO(
        LocalDateTime fechaHora,
        String motivo,
        String medicoId,
        String pacienteId) {}
