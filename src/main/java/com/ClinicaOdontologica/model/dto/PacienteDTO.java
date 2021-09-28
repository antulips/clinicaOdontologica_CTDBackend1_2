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
    //private OdontologoDto odontologo;

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
        //TODO AGREGAR DOMICILIO
        return "Paciente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                //", domicilio=" + domicilio.toString() +
                //", Odontólogo=" + odontologo.toString() +
                '}';
    }
}
