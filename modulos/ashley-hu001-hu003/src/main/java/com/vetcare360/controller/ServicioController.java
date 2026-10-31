package com.vetcare360.controller;

import com.vetcare360.domain.Servicio;
import com.vetcare360.dto.ServicioForm;
import com.vetcare360.service.ServicioService;
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

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    private final ServicioService service;

    public ServicioController(ServicioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("servicios", service.listar());
        return "servicios/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("servicioForm", new ServicioForm());
        model.addAttribute("accion", "/servicios");
        model.addAttribute("titulo", "Agregar servicio");
        return "servicios/formulario";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute ServicioForm servicioForm, BindingResult errors,
            Model model, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            model.addAttribute("accion", "/servicios");
            model.addAttribute("titulo", "Agregar servicio");
            return "servicios/formulario";
        }
        service.crear(servicioForm);
        ra.addFlashAttribute("ok", "Servicio agregado.");
        return "redirect:/servicios";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Servicio servicio = service.buscar(id);
            ServicioForm form = new ServicioForm();
            form.setNombre(servicio.getNombre());
            form.setImagen(servicio.getImagen());
            model.addAttribute("servicioForm", form);
            model.addAttribute("accion", "/servicios/" + id);
            model.addAttribute("titulo", "Editar servicio");
            return "servicios/formulario";
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
            return "redirect:/servicios";
        }
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute ServicioForm servicioForm,
            BindingResult errors, Model model, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            model.addAttribute("accion", "/servicios/" + id);
            model.addAttribute("titulo", "Editar servicio");
            return "servicios/formulario";
        }
        try {
            service.actualizar(id, servicioForm);
            ra.addFlashAttribute("ok", "Servicio actualizado.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/servicios";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.eliminar(id);
            ra.addFlashAttribute("ok", "Servicio eliminado.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/servicios";
    }
}
