package com.dbp.pc120251backendseccion3.domain;

import com.dbp.pc120251backendseccion3.dto.ConsultaDTO;
import com.dbp.pc120251backendseccion3.dto.ValidacionConsultaResponse;
import com.dbp.pc120251backendseccion3.exception.BusinessException;
import com.dbp.pc120251backendseccion3.exception.ResourceNotFoundException;
import com.dbp.pc120251backendseccion3.infrastructure.ConsultaRepository;
import com.dbp.pc120251backendseccion3.infrastructure.MedicoRepository;
import com.dbp.pc120251backendseccion3.infrastructure.PacienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository, MedicoRepository medicoRepository,
                           PacienteRepository pacienteRepository) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }



    public ValidacionConsultaResponse validarConsulta(String medicoId, String pacienteId) {
        // Validar existencia del médico
        if (!medicoRepository.existsByCodigoColegiatura(medicoId)) {
            return new ValidacionConsultaResponse("MEDICO_NO_EXISTE", "Médico no encontrado");
        }

        // Validar existencia del paciente
        if (!pacienteRepository.existsByNumeroHistoriaClinica(pacienteId)) {
            return new ValidacionConsultaResponse("PACIENTE_NO_EXISTE", "Paciente no encontrado");
        }

        // Obtener entidades completas para validaciones adicionales
        Medico medico = medicoRepository.findById(medicoId)
                .orElseThrow(() -> new ResourceNotFoundException("Médico no encontrado"));
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado"));

        // Validar estado del médico
        if (medico.getEstado() != Medico.EstadoMedico.ACTIVO) {
            return new ValidacionConsultaResponse("MEDICO_INACTIVO", "El médico no está activo");
        }

        // Validar estado del paciente
        if (paciente.getEstado() == Paciente.EstadoPaciente.FALLECIDO) {
            return new ValidacionConsultaResponse("PACIENTE_FALLECIDO", "El paciente está fallecido");
        }

        // Validación de especialidad (ejemplo para pediatría)
        if (medico.getEspecialidad() == Medico.Especialidad.PEDIATRIA &&
                paciente.getFechaNacimiento().until(LocalDate.now()).getYears() > 18) {
            return new ValidacionConsultaResponse("ESPECIALIDAD_INCORRECTA", "El médico no tiene la especialidad requerida");
        }

        return new ValidacionConsultaResponse("VALIDO", "Validación exitosa");
    }


    public List<Consulta> obtenerHistorialCompleto(String historiaId) {
        if (!pacienteRepository.existsById(historiaId)) {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
        return consultaRepository.findByPacienteNumeroHistoriaClinicaOrderByFechaHoraDesc(historiaId);
    }
}
