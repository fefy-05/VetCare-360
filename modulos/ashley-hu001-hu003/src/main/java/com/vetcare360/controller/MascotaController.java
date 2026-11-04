package com.vetcare360.controller;

import com.vetcare360.domain.Mascota;
import com.vetcare360.dto.MascotaForm;
import com.vetcare360.repository.PropietarioRepository;
import com.vetcare360.service.MascotaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequestMapping("/mascotas")
@Controller
public class MascotaController {

    private final MascotaService mascotaService;
    private final PropietarioRepository propietarioRepository;

    public MascotaController(MascotaService mascotaService,
                             PropietarioRepository propietarioRepository) {
        this.mascotaService = mascotaService;
        this.propietarioRepository = propietarioRepository;
    }

    @GetMapping
    public String listado(Model model) {
        model.addAttribute("mascotas", mascotaService.listar());
        return "mascotas/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("mascotaForm", new MascotaForm());
        cargarPropietarios(model);
        return "mascotas/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("mascotaForm") MascotaForm mascotaForm,
            BindingResult resultado,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {
            cargarPropietarios(model);
            return "mascotas/formulario";
        }

        try {
            mascotaService.guardar(mascotaForm);
            redirectAttributes.addFlashAttribute(
                    "mensaje", "La mascota se registró correctamente."
            );
            return "redirect:/mascotas";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            cargarPropietarios(model);
            return "mascotas/formulario";
        }
    }

    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {
            Mascota mascota = mascotaService.obtenerPorId(id);

            MascotaForm form = new MascotaForm();
            form.setNombre(mascota.getNombre());
            form.setEspecie(mascota.getEspecie());
            form.setRaza(mascota.getRaza());
            form.setSexo(mascota.getSexo());
            form.setFechaNacimiento(mascota.getFechaNacimiento());
            form.setPropietarioId(mascota.getPropietario().getId());

            model.addAttribute("mascotaForm", form);
            model.addAttribute("idMascota", id);
            cargarPropietarios(model);

            return "mascotas/formulario";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/mascotas";
        }
    }

    // Actualizar mascota
    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("mascotaForm") MascotaForm mascotaForm,
            BindingResult resultado,
            Model model,
            RedirectAttributes redirectAttributes) {

        model.addAttribute("idMascota", id);

        if (resultado.hasErrors()) {
            cargarPropietarios(model);
            return "mascotas/formulario";
        }

        try {
            mascotaService.actualizar(id, mascotaForm);

            redirectAttributes.addFlashAttribute(
                    "mensaje", "La mascota se actualizó correctamente."
            );

            return "redirect:/mascotas";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            cargarPropietarios(model);
            return "mascotas/formulario";
        }
    }

    // Eliminar mascota
    @PostMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {
            mascotaService.eliminar(id);

            redirectAttributes.addFlashAttribute(
                    "mensaje", "La mascota se eliminó correctamente."
            );

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/mascotas";
    }

    private void cargarPropietarios(Model model) {
        model.addAttribute("propietarios", propietarioRepository.findAll());
    }
}


