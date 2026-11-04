package com.vetcare360.repository;

import com.vetcare360.domain.ExpedienteMedico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpedienteMedicoRepository
        extends JpaRepository<ExpedienteMedico, Long> {

    List<ExpedienteMedico> findByMascotaId(Long mascotaId);
}