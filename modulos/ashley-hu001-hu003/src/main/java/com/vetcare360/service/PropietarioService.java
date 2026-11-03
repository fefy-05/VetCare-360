package com.vetcare360.service;

import com.vetcare360.domain.Propietario;
import com.vetcare360.dto.PropietarioForm;
import com.vetcare360.repository.MascotaRepository;
import com.vetcare360.repository.PropietarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropietarioService {

    private final PropietarioRepository propietarioRepository;
    private final MascotaRepository mascotaRepository;

    public PropietarioService(
            PropietarioRepository propietarioRepository,
            MascotaRepository mascotaRepository) {
        this.propietarioRepository = propietarioRepository;
        this.mascotaRepository = mascotaRepository;
    }

    public List<Propietario> listar() {
        return propietarioRepository.findAll();
    }

    public Propietario obtenerPorId(Long id) {
        return propietarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El propietario no existe."));
    }

    public Propietario guardar(PropietarioForm form) {

        if (propietarioRepository.existsByIdentificacion(
                form.getIdentificacion())) {
            throw new IllegalArgumentException(
                    "La identificación ya se encuentra registrada.");
        }

        if (propietarioRepository.existsByCorreo(form.getCorreo())) {
            throw new IllegalArgumentException(
                    "El correo electrónico ya se encuentra registrado.");
        }

        Propietario propietario = new Propietario();

        propietario.setIdentificacion(form.getIdentificacion());
        propietario.setNombre(form.getNombre());
        propietario.setApellidos(form.getApellidos());
        propietario.setCorreo(form.getCorreo());
        propietario.setTelefono(form.getTelefono());
        propietario.setDireccion(form.getDireccion());

        return propietarioRepository.save(propietario);
    }

    @Transactional
    public Propietario actualizar(Long id, PropietarioForm form) {

        Propietario propietario = obtenerPorId(id);

        if (propietarioRepository.existsByIdentificacionAndIdNot(
                form.getIdentificacion(), id)) {
            throw new IllegalArgumentException(
                    "La identificación ya se encuentra registrada.");
        }

        if (propietarioRepository.existsByCorreoAndIdNot(
                form.getCorreo(), id)) {
            throw new IllegalArgumentException(
                    "El correo electrónico ya se encuentra registrado.");
        }

        propietario.setIdentificacion(form.getIdentificacion());
        propietario.setNombre(form.getNombre());
        propietario.setApellidos(form.getApellidos());
        propietario.setCorreo(form.getCorreo());
        propietario.setTelefono(form.getTelefono());
        propietario.setDireccion(form.getDireccion());

        return propietarioRepository.save(propietario);
    }

    @Transactional
    public void eliminar(Long id) {

        Propietario propietario = obtenerPorId(id);

        if (mascotaRepository.existsByPropietario_Id(id)) {
            throw new IllegalArgumentException(
                    "No se puede eliminar este propietario porque tiene "
                    + "mascotas asociadas. Primero debe gestionar sus mascotas.");
        }

        propietarioRepository.delete(propietario);
    }
}

