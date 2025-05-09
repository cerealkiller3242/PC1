package com.dbp.pc120251backendseccion3.domain;

import com.dbp.pc120251backendseccion3.infrastructure.ConsultaRepository;
import com.dbp.pc120251backendseccion3.infrastructure.MedicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final ConsultaRepository consultaRepository;

    @Autowired
    public MedicoService(MedicoRepository medicoRepository, ConsultaRepository consultaRepository) {
        this.medicoRepository = medicoRepository;
        this.consultaRepository = consultaRepository;
    }


}
