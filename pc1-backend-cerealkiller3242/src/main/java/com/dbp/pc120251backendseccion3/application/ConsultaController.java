package com.dbp.pc120251backendseccion3.application;

import com.dbp.pc120251backendseccion3.domain.Consulta;
import com.dbp.pc120251backendseccion3.domain.ConsultaService;
import com.dbp.pc120251backendseccion3.dto.ConsultaDTO;
import com.dbp.pc120251backendseccion3.dto.ValidacionConsultaResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class ConsultaController {

    private final ConsultaService consultaService;

    @Autowired
    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }



    @GetMapping("/consultas/validar/{medicoId}/{pacienteId}")
    public ResponseEntity<ValidacionConsultaResponse> validarConsulta(
            @PathVariable String medicoId,
            @PathVariable String pacienteId) {
        return ResponseEntity.ok(consultaService.validarConsulta(medicoId, pacienteId));
    }
}