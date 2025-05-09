package com.dbp.pc120251backendseccion3.infrastructure;

import com.dbp.pc120251backendseccion3.domain.Consulta;
import com.dbp.pc120251backendseccion3.domain.Medico;
import com.dbp.pc120251backendseccion3.domain.Paciente;
import com.dbp.pc120251backendseccion3.dto.ValidacionConsultaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


import com.dbp.pc120251backendseccion3.domain.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM Consulta c WHERE c.medico.codigoColegiatura = :codigoMedico " +
            "AND c.fechaHora = :fechaHora")



    List<Consulta> findByPacienteNumeroHistoriaClinicaOrderByFechaHoraDesc(String numeroHistoriaClinica);
}