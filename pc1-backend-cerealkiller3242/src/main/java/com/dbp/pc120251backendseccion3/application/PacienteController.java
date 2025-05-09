package com.dbp.pc120251backendseccion3.application;

import com.dbp.pc120251backendseccion3.domain.Consulta;
import com.dbp.pc120251backendseccion3.domain.Paciente;
import com.dbp.pc120251backendseccion3.domain.PacienteService;
import com.dbp.pc120251backendseccion3.dto.ActualizarEstadoDTO;
import com.dbp.pc120251backendseccion3.dto.ActualizarNombresDTO;
import com.dbp.pc120251backendseccion3.dto.AsignarMedicoDTO;
import com.dbp.pc120251backendseccion3.dto.PacienteDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PacienteController {

    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping("/pacientes")
    public ResponseEntity<Paciente> crearPaciente(@Valid @RequestBody PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteService.crearPaciente(pacienteDTO);
        return ResponseEntity.created(URI.create("/api/pacientes/" + paciente.getNumeroHistoriaClinica())).body(paciente);
    }

    @GetMapping("/pacientes/{historiaId}")
    public ResponseEntity<Paciente> obtenerPaciente(@PathVariable String historiaId) {
        return ResponseEntity.ok(pacienteService.obtenerPorId(historiaId));
    }

    @PutMapping("/pacientes/{historiaId}/nombres")
    public ResponseEntity<Paciente> actualizarNombres(
            @PathVariable String historiaId,
            @Valid @RequestBody ActualizarNombresDTO nombresDTO) {
        return ResponseEntity.ok(pacienteService.actualizarNombres(historiaId, nombresDTO));
    }

    @PutMapping("/pacientes/{historiaId}/medico-cabecera")
    public ResponseEntity<Paciente> asignarMedicoCabecera(
            @PathVariable String historiaId,
            @RequestBody AsignarMedicoDTO medicoDTO) {
        return ResponseEntity.ok(pacienteService.asignarMedicoCabecera(historiaId, medicoDTO.medicoId()));
    }

    @DeleteMapping("/pacientes/{historiaId}")
    public ResponseEntity<Void> eliminarPaciente(@PathVariable String historiaId) {
        pacienteService.eliminarPaciente(historiaId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pacientes")
    public ResponseEntity<List<Paciente>> listarPacientes(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Paciente.EstadoPaciente estado) {
        return ResponseEntity.ok(pacienteService.listarConFiltros(nombre, estado));
    }


    @PutMapping("/pacientes/{historiaId}/estado")
    public ResponseEntity<Paciente> actualizarEstado(
            @PathVariable String historiaId,
            @RequestBody ActualizarEstadoDTO estadoDTO) {
        return ResponseEntity.ok(pacienteService.actualizarEstado(historiaId, estadoDTO.estado()));
    }
}