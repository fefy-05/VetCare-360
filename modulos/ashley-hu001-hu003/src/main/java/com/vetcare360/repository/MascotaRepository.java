package com.vetcare360.repository;

import com.vetcare360.domain.Mascota;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    @Override
    @EntityGraph(attributePaths = {"propietario"})
    List<Mascota> findAll();
    
    boolean existsByPropietario_Id(Long propietarioId);
}

