package com.vetcare360.repository;

import com.vetcare360.domain.ConsultaMedica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaMedicaRepository
        extends JpaRepository<ConsultaMedica, Long> {

    List<ConsultaMedica> findByCitaMascotaIdOrderByFechaRegistroDesc(Long mascotaId);
}
