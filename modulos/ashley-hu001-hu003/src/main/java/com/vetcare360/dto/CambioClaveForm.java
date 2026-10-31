package com.vetcare360.dto;
import jakarta.validation.constraints.*;
public class CambioClaveForm {
 @NotBlank private String actual;
 @NotBlank @Size(min=8,message="La contraseña debe tener al menos 8 caracteres") private String nueva;
 @NotBlank private String confirmar;
 public String getActual(){return actual;}public void setActual(String v){actual=v;}
 public String getNueva(){return nueva;}public void setNueva(String v){nueva=v;}
 public String getConfirmar(){return confirmar;}public void setConfirmar(String v){confirmar=v;}
}