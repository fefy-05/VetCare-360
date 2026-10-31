package com.vetcare360.dto;
import jakarta.validation.constraints.*;
public class RegistroForm {
 @NotBlank private String nombre;
 @NotBlank @Email private String correo;
 @NotBlank @Size(min=8,message="La contraseña debe tener al menos 8 caracteres") private String password;
 public String getNombre(){return nombre;}public void setNombre(String v){nombre=v;}
 public String getCorreo(){return correo;}public void setCorreo(String v){correo=v;}
 public String getPassword(){return password;}public void setPassword(String v){password=v;}
}