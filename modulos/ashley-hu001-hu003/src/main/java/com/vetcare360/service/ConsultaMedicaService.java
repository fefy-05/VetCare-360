
package com.vetcare360.service;

import com.vetcare360.domain.Cita;
import com.vetcare360.domain.ConsultaMedica;
import com.vetcare360.repository.CitaRepository;
import com.vetcare360.repository.ConsultaMedicaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConsultaMedicaService {

    private final ConsultaMedicaRepository consultaRepository;
    private final CitaRepository citaRepository;

    public ConsultaMedicaService(
            ConsultaMedicaRepository consultaRepository,
            CitaRepository citaRepository) {
        this.consultaRepository = consultaRepository;
        this.citaRepository = citaRepository;
    }

    public List<ConsultaMedica> listar() {
        return consultaRepository.findAll();
    }

    public ConsultaMedica obtenerPorId(Long id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la consulta médica."));
    }

    @Transactional
    public void guardar(ConsultaMedica consulta, Long citaId) {

        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La cita seleccionada no existe."));

        consulta.setCita(cita);

        if (consulta.getFechaRegistro() == null) {
            consulta.setFechaRegistro(
                    java.time.LocalDateTime.now());
        }

        consultaRepository.save(consulta);
    }

    @Transactional
    public void actualizar(Long id, ConsultaMedica datos) {

        ConsultaMedica consulta = obtenerPorId(id);

        consulta.setDiagnostico(datos.getDiagnostico());
        consulta.setObservaciones(datos.getObservaciones());

        consultaRepository.save(consulta);
    }
}
