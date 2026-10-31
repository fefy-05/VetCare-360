package com.vetcare360.config;

import com.vetcare360.domain.Servicio;
import com.vetcare360.repository.ServicioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Carga los servicios que ofrece la veterinaria aunque no se hayan agregado mas.
 */
@Component
public class DatosIniciales implements CommandLineRunner {

    private final ServicioRepository servicios;

    public DatosIniciales(ServicioRepository servicios) {
        this.servicios = servicios;
    }

    @Override
    public void run(String... args) {
        if (servicios.count() > 0) {
            return;
        }
        String[] nombres = {
            "Grooming",
            "Revisión general",
            "Vacunación",
            "Cirugías",
            "Emergencias",
            "Odontología veterinaria"
        };
        for (String nombre : nombres) {
            Servicio servicio = new Servicio();
            servicio.setNombre(nombre);
            servicios.save(servicio);
        }
    }
}
