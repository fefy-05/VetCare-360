package com.vetcare360.controller;

import com.vetcare360.domain.Cita;
import com.vetcare360.dto.CitaForm;
import com.vetcare360.service.CitaService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    // Listar y filtrar citas.
    @GetMapping
    public String listar(
            @RequestParam(required = false) Long mascotaId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            Model model) {

        model.addAttribute("citas", citaService.filtrar(mascotaId, fecha));
        model.addAttribute("mascotas", citaService.listarMascotas());
        model.addAttribute("mascotaId", mascotaId);
        model.addAttribute("fecha", fecha);

        Map<Long, Long> cantidadPorMascota = new HashMap<>();

        for (var mascota : citaService.listarMascotas()) {
            cantidadPorMascota.put(
                    mascota.getId(),
                    citaService.contarPorMascota(mascota.getId())
            );
        }

        model.addAttribute("cantidadPorMascota", cantidadPorMascota);

        return "citas/listado";
    }

    // Mostrar formulario para registrar una cita.
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("citaForm", new CitaForm());
        model.addAttribute("mascotas", citaService.listarMascotas());
        model.addAttribute("titulo", "Registrar cita");
        model.addAttribute("accion", "/citas/guardar");

        return "citas/formulario";
    }

    // Guardar una cita nueva.
    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("citaForm") CitaForm citaForm,
            BindingResult resultado,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {
            model.addAttribute("mascotas", citaService.listarMascotas());
            model.addAttribute("titulo", "Registrar cita");
            model.addAttribute("accion", "/citas/guardar");
            return "citas/formulario";
        }

        citaService.guardar(citaForm);
        redirectAttributes.addFlashAttribute(
                "mensaje", "La cita se registró correctamente.");

        return "redirect:/citas";
    }

    // Mostrar formulario para editar una cita.
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Cita cita = citaService.obtenerPorId(id);

        CitaForm form = new CitaForm();
        form.setFecha(cita.getFecha());
        form.setHora(cita.getHora());
        form.setMotivo(cita.getMotivo());
        form.setEstado(cita.getEstado());
        form.setMascotaId(cita.getMascota().getId());

        model.addAttribute("citaForm", form);
        model.addAttribute("mascotas", citaService.listarMascotas());
        model.addAttribute("titulo", "Editar cita");
        model.addAttribute("accion", "/citas/actualizar/" + id);

        return "citas/formulario";
    }

    // Actualizar una cita.
    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("citaForm") CitaForm citaForm,
            BindingResult resultado,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {
            model.addAttribute("mascotas", citaService.listarMascotas());
            model.addAttribute("titulo", "Editar cita");
            model.addAttribute("accion", "/citas/actualizar/" + id);
            return "citas/formulario";
        }

        citaService.actualizar(id, citaForm);
        redirectAttributes.addFlashAttribute(
                "mensaje", "La cita se actualizó correctamente.");

        return "redirect:/citas";
    }

    // Eliminar una cita.
    @PostMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        citaService.eliminar(id);
        redirectAttributes.addFlashAttribute(
                "mensaje", "La cita se eliminó correctamente.");

        return "redirect:/citas";
    }
}
