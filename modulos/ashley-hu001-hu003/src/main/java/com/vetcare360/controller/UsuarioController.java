package com.vetcare360.controller;

import com.vetcare360.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/usuarios")
    public String listar(Authentication auth, Model model) {
        model.addAttribute("usuarios", service.listar());
        model.addAttribute("correoActual", auth.getName());
        return "usuarios/listado";
    }

    @PostMapping("/usuarios/{id}/rol")
    public String cambiarRol(@PathVariable Long id, @RequestParam String rol,
            Authentication auth, RedirectAttributes ra) {
        try {
            service.cambiarRol(id, rol, auth.getName());
            ra.addFlashAttribute("ok", "Rol actualizado. El cambio se aplica cuando ese usuario vuelva a iniciar sesión.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/{id}/eliminar")
    public String eliminar(@PathVariable Long id, Authentication auth, RedirectAttributes ra) {
        try {
            service.eliminar(id, auth.getName());
            ra.addFlashAttribute("ok", "Usuario eliminado.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/usuarios";
    }
}
