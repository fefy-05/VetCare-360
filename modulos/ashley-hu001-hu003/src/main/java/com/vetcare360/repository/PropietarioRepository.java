package com.vetcare360.repository;

import com.vetcare360.domain.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PropietarioRepository
        extends JpaRepository<Propietario, Long> {

    boolean existsByIdentificacion(String identificacion);

    boolean existsByCorreo(String correo);

    boolean existsByIdentificacionAndIdNot(
            String identificacion, Long id);

    boolean existsByCorreoAndIdNot(
            String correo, Long id);

    Optional<Propietario> findByIdentificacion(String identificacion);

    Optional<Propietario> findByCorreo(String correo);
}

