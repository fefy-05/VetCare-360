package com.vetcare360.controller;

import com.vetcare360.dto.*;
import com.vetcare360.service.UsuarioService;
import jakarta.validation.Valid;
import java.time.LocalTime;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccesoController {

    private final UsuarioService service;

    public AccesoController(UsuarioService s) {
        service = s;
    }

    @GetMapping("/")
    public String raiz() {
        return "redirect:/inicio";
    }

    @GetMapping("/login")
    public String login() {
        return "login/login";
    }

    @GetMapping("/registro")
    public String registro(Model m) {
        m.addAttribute("registroForm", new RegistroForm());
        return "registro/registro";
    }

    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute RegistroForm registroForm, BindingResult errors, Model m, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return "registro/registro";
        }
        try {
            service.registrar(registroForm);
            ra.addFlashAttribute("ok", "Cuenta creada. Ya puedes iniciar sesión.");
            return "redirect:/login";
        } catch (IllegalArgumentException ex) {
            m.addAttribute("error", ex.getMessage());
            return "registro/registro";
        }
    }

    @GetMapping("/inicio")
    public String inicio(Authentication a, Model m) {
        String nombreCompleto = service.nombreDe(a.getName());
        String primerNombre = nombreCompleto.trim().split("\\s+")[0];
        int hora = LocalTime.now().getHour();
        String saludo;
        if (hora < 12) {
            saludo = "Buenos días";
        } else if (hora < 19) {
            saludo = "Buenas tardes";
        } else {
            saludo = "Buenas noches";
        }
        m.addAttribute("correo", a.getName());
        m.addAttribute("nombre", primerNombre);
        m.addAttribute("saludo", saludo);
        return "inicio/inicio";
    }

    @GetMapping("/cambiar-clave")
    public String clave(Model m) {
        m.addAttribute("cambioClaveForm", new CambioClaveForm());
        return "clave/cambiar-clave";
    }

    @PostMapping("/cambiar-clave")
    public String cambiar(@Valid @ModelAttribute CambioClaveForm cambioClaveForm, BindingResult errors, Authentication a, Model m, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return "clave/cambiar-clave";
        }
        try {
            service.cambiarClave(a.getName(), cambioClaveForm);
            ra.addFlashAttribute("ok", "Contraseña actualizada.");
            return "redirect:/inicio";
        } catch (IllegalArgumentException ex) {
            m.addAttribute("error", ex.getMessage());
            return "clave/cambiar-clave";
        }
    }
}
