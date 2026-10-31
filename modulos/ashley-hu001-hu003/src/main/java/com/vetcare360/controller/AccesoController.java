package com.vetcare360.controller;
import com.vetcare360.dto.*;
import com.vetcare360.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class AccesoController {
 private final UsuarioService service;
 public AccesoController(UsuarioService s){service=s;}
 @GetMapping("/")public String raiz(){return "redirect:/inicio";}
 @GetMapping("/login")public String login(){return "login";}
 @GetMapping("/registro")public String registro(Model m){m.addAttribute("registroForm",new RegistroForm());return "registro";}
 @PostMapping("/registro")public String registrar(@Valid @ModelAttribute RegistroForm registroForm,BindingResult errors,Model m,RedirectAttributes ra){
  if(errors.hasErrors())return "registro";
  try{service.registrar(registroForm);ra.addFlashAttribute("ok","Cuenta creada. Ya puedes iniciar sesión.");return "redirect:/login";}
  catch(IllegalArgumentException ex){m.addAttribute("error",ex.getMessage());return "registro";}
 }
 @GetMapping("/inicio")public String inicio(Authentication a,Model m){m.addAttribute("correo",a.getName());return "inicio";}
 @GetMapping("/cambiar-clave")public String clave(Model m){m.addAttribute("cambioClaveForm",new CambioClaveForm());return "cambiar-clave";}
 @PostMapping("/cambiar-clave")public String cambiar(@Valid @ModelAttribute CambioClaveForm cambioClaveForm,BindingResult errors,Authentication a,Model m,RedirectAttributes ra){
  if(errors.hasErrors())return "cambiar-clave";
  try{service.cambiarClave(a.getName(),cambioClaveForm);ra.addFlashAttribute("ok","Contraseña actualizada.");return "redirect:/inicio";}
  catch(IllegalArgumentException ex){m.addAttribute("error",ex.getMessage());return "cambiar-clave";}
 }
}