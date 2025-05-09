package com.dbp.pc120251backendseccion3.domain;

import com.dbp.pc120251backendseccion3.dto.ActualizarNombresDTO;
import com.dbp.pc120251backendseccion3.dto.PacienteDTO;
import com.dbp.pc120251backendseccion3.exception.BusinessException;
import com.dbp.pc120251backendseccion3.exception.ResourceAlreadyExistsException;
import com.dbp.pc120251backendseccion3.exception.ResourceNotFoundException;
import com.dbp.pc120251backendseccion3.infrastructure.MedicoRepository;
import com.dbp.pc120251backendseccion3.infrastructure.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository, MedicoRepository medicoRepository) {
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    public Paciente crearPaciente(PacienteDTO pacienteDTO) {
        if (pacienteRepository.existsById(pacienteDTO.numeroHistoriaClinica())) {
            throw new ResourceAlreadyExistsException("El número de historia clínica ya existe");
        }

        Paciente paciente = new Paciente();
        paciente.setNumeroHistoriaClinica(pacienteDTO.numeroHistoriaClinica());
        paciente.setNombres(pacienteDTO.nombres());
        paciente.setApellidos(pacienteDTO.apellidos());
        paciente.setFechaNacimiento(pacienteDTO.fechaNacimiento());
        paciente.setSexo(pacienteDTO.sexo());
        paciente.setTipoSangre(pacienteDTO.tipoSangre());
        paciente.setAlergias(pacienteDTO.alergias());
        paciente.setCorreoElectronico(pacienteDTO.correoElectronico());
        paciente.setEstado(Paciente.EstadoPaciente.ACTIVO);

        return pacienteRepository.save(paciente);
    }

    public Paciente obtenerPorId(String historiaId) {
        return pacienteRepository.findById(historiaId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));
    }

    public Paciente actualizarNombres(String historiaId, ActualizarNombresDTO nombresDTO) {
        Paciente paciente = obtenerPorId(historiaId);
        paciente.setNombres(nombresDTO.nombres());
        paciente.setApellidos(nombresDTO.apellidos());
        return pacienteRepository.save(paciente);
    }

    public Paciente asignarMedicoCabecera(String historiaId, String medicoId) {
        Paciente paciente = obtenerPorId(historiaId);
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado"));

        paciente.setMedicoCabecera(medico);
        return pacienteRepository.save(paciente);
    }

    public void eliminarPaciente(String historiaId) {
        Paciente paciente = obtenerPorId(historiaId);

        if (!paciente.getConsultas().isEmpty()) {
            throw new BusinessException("No se puede eliminar un paciente con consultas registradas");
        }

        pacienteRepository.delete(paciente);
    }

    public List<Paciente> listarConFiltros(String nombre, Paciente.EstadoPaciente estado) {
        if (nombre != null && estado != null) {
            return pacienteRepository.buscarPacientesConFiltros(nombre, estado);
        } else if (nombre != null) {
            return pacienteRepository.buscarPacientesConFiltros(nombre, null);
        } else if (estado != null) {
            return pacienteRepository.findByEstado(estado);
        }
        return pacienteRepository.findAll();
    }

    public Paciente actualizarEstado(String historiaId, Paciente.EstadoPaciente estado) {
        Paciente paciente = obtenerPorId(historiaId);
        paciente.setEstado(estado);
        return pacienteRepository.save(paciente);
    }
}