package com.vetcare360.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

public class CitaForm {

    @NotNull(message = "La fecha de la cita es obligatoria.")
    private LocalDate fecha;

    @NotNull(message = "La hora de la cita es obligatoria.")
    private LocalTime hora;

    @NotBlank(message = "El motivo de la cita es obligatorio.")
    @Size(max = 255, message = "El motivo no puede superar los 255 caracteres.")
    private String motivo;

    @NotBlank(message = "Debe seleccionar un estado.")
    @Pattern(
        regexp = "Programada|Realizada|Cancelada",
        message = "Seleccione un estado válido."
    )
    private String estado;

    @NotNull(message = "Debe seleccionar una mascota.")
    private Long mascotaId;

    public CitaForm() {
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }
}

