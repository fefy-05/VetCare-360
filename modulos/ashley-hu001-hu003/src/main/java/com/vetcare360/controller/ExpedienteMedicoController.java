package com.vetcare360.controller;

import com.vetcare360.domain.ExpedienteMedico;
import com.vetcare360.service.ExpedienteMedicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/expedientes")
public class ExpedienteMedicoController {

    private final ExpedienteMedicoService expedienteService;

    public ExpedienteMedicoController(
            ExpedienteMedicoService expedienteService) {
        this.expedienteService = expedienteService;
    }

    // Listar todos los expedientes
    @GetMapping
    public String listar(Model model) {

        List<ExpedienteMedico> expedientes =
                expedienteService.listarExpedientes();

        model.addAttribute("expedientes", expedientes);

        return "expedientes/listado";
    }

    // Buscar expedientes por mascota
    @GetMapping("/mascota/{mascotaId}")
    public String consultarPorMascota(
            @PathVariable Long mascotaId,
            Model model) {

        List<ExpedienteMedico> expedientes =
                expedienteService.buscarPorMascota(mascotaId);

        model.addAttribute("expedientes", expedientes);
        model.addAttribute("mascotaId", mascotaId);

        return "expedientes/listado";
    }

    // Buscar expedientes mediante el formulario
    @GetMapping("/buscar")
    public String buscarPorMascota(
            @RequestParam Long mascotaId,
            Model model) {

        List<ExpedienteMedico> expedientes =
                expedienteService.buscarPorMascota(mascotaId);

        model.addAttribute("expedientes", expedientes);
        model.addAttribute("mascotaId", mascotaId);

        return "expedientes/listado";
    }

    // Mostrar detalle de un expediente
    @GetMapping("/{id}")
    public String verDetalle(
            @PathVariable Long id,
            Model model) {

        ExpedienteMedico expediente =
                expedienteService.buscarPorId(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Expediente no encontrado: " + id));

        Long mascotaId = expediente.getMascota().getId();

        model.addAttribute("expediente", expediente);

        model.addAttribute(
                "consultas",
                expedienteService.obtenerConsultasPorMascota(mascotaId)
        );

        return "expedientes/detalle";
    }
}