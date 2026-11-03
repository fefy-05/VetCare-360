package com.vetcare360.service;

import com.vetcare360.domain.Cita;
import com.vetcare360.domain.Mascota;
import com.vetcare360.dto.CitaForm;
import com.vetcare360.repository.CitaRepository;
import com.vetcare360.repository.MascotaRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;

    public CitaService(CitaRepository citaRepository,
                       MascotaRepository mascotaRepository) {
        this.citaRepository = citaRepository;
        this.mascotaRepository = mascotaRepository;
    }

    // Listar todas las citas, de la más reciente a la más antigua.
    public List<Cita> listar() {
        return citaRepository.findAllByOrderByFechaDescHoraDesc();
    }

    // Buscar una cita por su identificador.
    public Cita obtenerPorId(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la cita solicitada."));
    }

    // Registrar una nueva cita.
    public void guardar(CitaForm form) {
        Cita cita = new Cita();
        asignarDatos(cita, form);
        citaRepository.save(cita);
    }

    // Actualizar una cita existente.
    public void actualizar(Long id, CitaForm form) {
        Cita cita = obtenerPorId(id);
        asignarDatos(cita, form);
        citaRepository.save(cita);
    }

    // Eliminar una cita.
    public void eliminar(Long id) {
        Cita cita = obtenerPorId(id);
        citaRepository.delete(cita);
    }

    // Filtrar citas por mascota, fecha o ambos criterios.
    public List<Cita> filtrar(Long mascotaId, LocalDate fecha) {

        if (mascotaId != null && fecha != null) {
            return citaRepository
                    .findByMascota_IdAndFechaOrderByFechaDescHoraDesc(
                            mascotaId, fecha);
        }

        if (mascotaId != null) {
            return citaRepository
                    .findByMascota_IdOrderByFechaDescHoraDesc(mascotaId);
        }

        if (fecha != null) {
            return citaRepository.findByFechaOrderByHoraDesc(fecha);
        }

        return listar();
    }

    // Obtener la cantidad de citas asociadas a una mascota.
    public long contarPorMascota(Long mascotaId) {
        return citaRepository.countByMascota_Id(mascotaId);
    }

    // Obtener las mascotas disponibles para asociarlas a una cita.
    public List<Mascota> listarMascotas() {
        return mascotaRepository.findAll();
    }

    // Asignar los datos del formulario a la entidad.
    private void asignarDatos(Cita cita, CitaForm form) {

        Mascota mascota = mascotaRepository.findById(form.getMascotaId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La mascota seleccionada no existe."));

        cita.setFecha(form.getFecha());
        cita.setHora(form.getHora());
        cita.setMotivo(form.getMotivo().trim());
        cita.setEstado(form.getEstado());
        cita.setMascota(mascota);
    }
}

