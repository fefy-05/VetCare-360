package com.vetcare360.service;

import com.vetcare360.domain.ConsultaMedica;
import com.vetcare360.domain.ExpedienteMedico;
import com.vetcare360.repository.ConsultaMedicaRepository;
import com.vetcare360.repository.ExpedienteMedicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpedienteMedicoService {

    private final ExpedienteMedicoRepository expedienteRepository;
    private final ConsultaMedicaRepository consultaRepository;

    public ExpedienteMedicoService(
            ExpedienteMedicoRepository expedienteRepository,
            ConsultaMedicaRepository consultaRepository) {
        this.expedienteRepository = expedienteRepository;
        this.consultaRepository = consultaRepository;
    }

    public List<ExpedienteMedico> listarExpedientes() {
        return expedienteRepository.findAll();
    }

    public Optional<ExpedienteMedico> buscarPorId(Long id) {
        return expedienteRepository.findById(id);
    }

    public List<ExpedienteMedico> buscarPorMascota(Long mascotaId) {
        return expedienteRepository.findByMascotaId(mascotaId);
    }

    public List<ConsultaMedica> obtenerConsultasPorMascota(Long mascotaId) {
        return consultaRepository
                .findByCitaMascotaIdOrderByFechaRegistroDesc(mascotaId);
    }
}