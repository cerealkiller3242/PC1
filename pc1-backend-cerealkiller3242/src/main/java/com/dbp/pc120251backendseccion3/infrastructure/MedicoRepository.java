package com.dbp.pc120251backendseccion3.infrastructure;

import com.dbp.pc120251backendseccion3.domain.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


import com.dbp.pc120251backendseccion3.domain.Medico;
import com.dbp.pc120251backendseccion3.domain.Medico.Especialidad;
import com.dbp.pc120251backendseccion3.domain.Medico.EstadoMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, String> {

    Optional<Medico> findByCodigoColegiatura(String codigoColegiatura);

    List<Medico> findByEspecialidadAndEstado(Especialidad especialidad, EstadoMedico estado);

    boolean existsByCodigoColegiatura(String codigoColegiatura);



    @Query("SELECT m FROM Medico m WHERE " +
            "LOWER(CONCAT(m.nombres, ' ', m.apellidos)) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Medico> buscarPorNombreCompleto(@Param("nombre") String nombre);
}
