package com.vetcare360.service;

import com.vetcare360.domain.Servicio;
import com.vetcare360.dto.ServicioForm;
import com.vetcare360.repository.ServicioRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioService {

    private final ServicioRepository repo;

    public ServicioService(ServicioRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Servicio> listar() {
        return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional(readOnly = true)
    public Servicio buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El servicio no existe."));
    }

    @Transactional
    public void crear(ServicioForm form) {
        Servicio servicio = new Servicio();
        aplicar(servicio, form);
        repo.save(servicio);
    }

    @Transactional
    public void actualizar(Long id, ServicioForm form) {
        Servicio servicio = buscar(id);
        aplicar(servicio, form);
        repo.save(servicio);
    }

    @Transactional
    public void eliminar(Long id) {
        Servicio servicio = buscar(id);
        repo.delete(servicio);
    }

    private void aplicar(Servicio servicio, ServicioForm form) {
        servicio.setNombre(form.getNombre().trim());
        String imagen = form.getImagen();
        if (imagen == null || imagen.isBlank()) {
            servicio.setImagen(null);
        } else {
            servicio.setImagen(imagen.trim());
        }
    }
}
