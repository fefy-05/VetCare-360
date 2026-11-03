package com.vetcare360.repository;

import com.vetcare360.domain.Cita;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    @EntityGraph(attributePaths = {"mascota", "mascota.propietario"})
    List<Cita> findAllByOrderByFechaDescHoraDesc();

    @EntityGraph(attributePaths = {"mascota", "mascota.propietario"})
    List<Cita> findByMascota_IdOrderByFechaDescHoraDesc(Long mascotaId);

    @EntityGraph(attributePaths = {"mascota", "mascota.propietario"})
    List<Cita> findByFechaOrderByHoraDesc(LocalDate fecha);

    List<Cita> findByMascota_IdAndFechaOrderByFechaDescHoraDesc(Long mascotaId, LocalDate fecha);
    
    long countByMascota_Id(Long mascotaId);
}
