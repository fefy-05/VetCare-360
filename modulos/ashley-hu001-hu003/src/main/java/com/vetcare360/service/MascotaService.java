package com.vetcare360.service;

import com.vetcare360.domain.Mascota;
import com.vetcare360.domain.Propietario;
import com.vetcare360.dto.MascotaForm;
import com.vetcare360.repository.MascotaRepository;
import com.vetcare360.repository.PropietarioRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final PropietarioRepository propietarioRepository;

    public MascotaService(MascotaRepository mascotaRepository,
                          PropietarioRepository propietarioRepository) {
        this.mascotaRepository = mascotaRepository;
        this.propietarioRepository = propietarioRepository;
    }

    public List<Mascota> listar() {
        return mascotaRepository.findAll();
    }

    public Mascota obtenerPorId(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "La mascota no existe."
                ));
    }

    @Transactional
    public void guardar(MascotaForm form) {
        Mascota mascota = new Mascota();
        asignarDatos(mascota, form);
        mascotaRepository.save(mascota);
    }

    @Transactional
    public void actualizar(Long id, MascotaForm form) {
        Mascota mascota = obtenerPorId(id);
        asignarDatos(mascota, form);
        mascotaRepository.save(mascota);
    }

    @Transactional
    public void eliminar(Long id) {
        Mascota mascota = obtenerPorId(id);
        mascotaRepository.delete(mascota);
    }

    private void asignarDatos(Mascota mascota, MascotaForm form) {

        if (form.getPropietarioId() == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un propietario."
            );
        }

        Propietario propietario = propietarioRepository
                .findById(form.getPropietarioId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "El propietario seleccionado no existe."
                ));

        mascota.setNombre(form.getNombre().trim());
        mascota.setEspecie(form.getEspecie().trim());
        mascota.setRaza(form.getRaza() == null
                ? null : form.getRaza().trim());
        mascota.setSexo(form.getSexo());
        mascota.setFechaNacimiento(form.getFechaNacimiento());
        mascota.setPropietario(propietario);
    }
}


