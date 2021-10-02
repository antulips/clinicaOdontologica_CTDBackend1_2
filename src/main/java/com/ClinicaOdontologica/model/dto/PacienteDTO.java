package com.ClinicaOdontologica.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter @Setter
public class PacienteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaIngreso;
    private DomicilioDTO domicilio;

    public PacienteDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PacienteDTO that = (PacienteDTO) o;
        return apellido.equals(that.apellido) && nombre.equals(that.nombre) && dni.equals(that.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido, dni, fechaIngreso);
    }

    @Override
    public String toString() {
        return "Paciente:" +
                "\n\tid: '" + id + "'" +
                "\n\tD.U.: '" + dni + "'" +
                "\n\tNombre: '" + nombre + "'" +
                "\n\tApellido: '" + apellido + "'" +
                "\n\tFecha de Ingreso: '" + fechaIngreso + "'" +
                "\n\tDomicilio: '" + domicilio.toString() + "' \n";
    }
}
