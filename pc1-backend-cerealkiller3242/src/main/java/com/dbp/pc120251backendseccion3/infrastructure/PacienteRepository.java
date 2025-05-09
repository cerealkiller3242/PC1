package com.dbp.pc120251backendseccion3.infrastructure;

import com.dbp.pc120251backendseccion3.domain.Consulta;
import com.dbp.pc120251backendseccion3.domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface PacienteRepository extends JpaRepository<Paciente, String> {

    Optional<Paciente> findByNumeroHistoriaClinica(String numeroHistoriaClinica);

    List<Paciente> findByEstado(Paciente.EstadoPaciente estado);

    List<Paciente> findByMedicoCabeceraCodigoColegiatura(String codigoColegiatura);

    boolean existsByNumeroHistoriaClinica(String numeroHistoriaClinica);

    @Query("SELECT p FROM Paciente p WHERE " +
            "LOWER(CONCAT(p.nombres, ' ', p.apellidos)) LIKE LOWER(CONCAT('%', :nombre, '%')) AND " +
            "(:estado IS NULL OR p.estado = :estado)")
    List<Paciente> buscarPacientesConFiltros(
            @Param("nombre") String nombre,
            @Param("estado") Paciente.EstadoPaciente estado);

}
