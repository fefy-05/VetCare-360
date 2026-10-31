package com.vetcare360.repository;
import com.vetcare360.domain.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
 Optional<Usuario> findByCorreo(String correo);
 boolean existsByCorreo(String correo);
}