package com.ClinicaOdontologica.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter @Setter
public class OdontologoDTO implements Serializable {

    private Long id;
    private String apellido;
    private String nombre;
    private String matricula;

    public OdontologoDTO() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OdontologoDTO that = (OdontologoDTO) o;
        return apellido.equals(that.apellido) && nombre.equals(that.nombre) && matricula.equals(that.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido, matricula);
    }

    @Override
    public String toString() {
        return "Odontólogo:" +
                "\n\tid: '" + id + "'" +
                "\n\tMatrícula: '" + matricula + "'" +
                "\n\tNombre: '" + nombre + "'" +
                "\n\tApellido: '" + apellido + "' \n";
    }

}
