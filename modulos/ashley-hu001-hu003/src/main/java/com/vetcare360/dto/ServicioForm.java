package com.vetcare360.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ServicioForm {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede pasar de 100 caracteres")
    private String nombre;

    @Size(max = 500, message = "La ruta de la imagen es demasiado larga")
    @Pattern(regexp = "^$|^(https?://|/)\\S+$",
            message = "Debe empezar con / (imagen del proyecto) o con http:// o https://")
    private String imagen;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
