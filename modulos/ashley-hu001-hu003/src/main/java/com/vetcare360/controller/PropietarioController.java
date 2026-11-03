package com.vetcare360.controller;

import com.vetcare360.domain.Propietario;
import com.vetcare360.dto.PropietarioForm;
import com.vetcare360.service.PropietarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/propietarios")
public class PropietarioController {

    private final PropietarioService propietarioService;

    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    @GetMapping
    public String listado(Model model) {
        model.addAttribute("propietarios",
                propietarioService.listar());

        return "propietarios/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("propietarioForm", new PropietarioForm());
        return "propietarios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("propietarioForm")
            PropietarioForm form,
            BindingResult resultado,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {
            return "propietarios/formulario";
        }

        try {
            propietarioService.guardar(form);

            redirectAttributes.addFlashAttribute(
                    "mensaje", "Propietario registrado correctamente.");

            return "redirect:/propietarios";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "propietarios/formulario";
        }
    }

    // Mostrar formulario de edición
    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {

        try {
            Propietario propietario = propietarioService.obtenerPorId(id);

            PropietarioForm form = new PropietarioForm();
            form.setIdentificacion(propietario.getIdentificacion());
            form.setNombre(propietario.getNombre());
            form.setApellidos(propietario.getApellidos());
            form.setCorreo(propietario.getCorreo());
            form.setTelefono(propietario.getTelefono());
            form.setDireccion(propietario.getDireccion());

            model.addAttribute("propietarioForm", form);
            model.addAttribute("idPropietario", id);

            return "propietarios/formulario";

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/propietarios";
        }
    }

    // Actualizar propietario
    @PostMapping("/actualizar/{id}")
    public String actualizar(
            @PathVariable Long id,
            @Valid @ModelAttribute("propietarioForm")
            PropietarioForm form,
            BindingResult resultado,
            Model model,
            RedirectAttributes redirectAttributes) {

        model.addAttribute("idPropietario", id);

        if (resultado.hasErrors()) {
            return "propietarios/formulario";
        }

        try {
            propietarioService.actualizar(id, form);

            redirectAttributes.addFlashAttribute(
                    "mensaje", "Propietario actualizado correctamente.");

            return "redirect:/propietarios";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "propietarios/formulario";
        }
    }

    // Eliminar propietario
    @PostMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {
            propietarioService.eliminar(id);

            redirectAttributes.addFlashAttribute(
                    "mensaje", "Propietario eliminado correctamente.");

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/propietarios";
    }
}


