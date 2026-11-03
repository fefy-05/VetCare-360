
package com.vetcare360.controller;

import com.vetcare360.domain.ConsultaMedica;
import com.vetcare360.service.CitaService;
import com.vetcare360.service.ConsultaMedicaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/consultas")
public class ConsultaMedicaController {

    private final ConsultaMedicaService consultaService;
    private final CitaService citaService;

    public ConsultaMedicaController(
            ConsultaMedicaService consultaService,
            CitaService citaService) {
        this.consultaService = consultaService;
        this.citaService = citaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("consultas", consultaService.listar());
        return "consultas/listado";
    }

    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("consulta", new ConsultaMedica());
        model.addAttribute("citas", citaService.listar());
        return "consultas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute("consulta") ConsultaMedica consulta,
            @RequestParam("citaId") Long citaId) {

        consultaService.guardar(consulta, citaId);
        return "redirect:/consultas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        ConsultaMedica consulta = consultaService.obtenerPorId(id);

        model.addAttribute("consulta", consulta);
        model.addAttribute("citas", citaService.listar());

        return "consultas/formulario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @ModelAttribute("consulta") ConsultaMedica consulta) {

        consultaService.actualizar(id, consulta);
        return "redirect:/consultas";
    }
}
