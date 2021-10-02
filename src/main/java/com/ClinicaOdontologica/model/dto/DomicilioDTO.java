package com.ClinicaOdontologica.model.dto;

import lombok.Data;

@Data
public class DomicilioDTO {

    private Long id;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;

    @Override
    public String toString() {
        return "Domicilio:" +
                "\n\tid: '" + id + "'" +
                "\n\tCalle: '" + calle + "'" +
                "\n\tNúmero: '" + numero + "'" +
                "\n\tLocalidad: '" + localidad + "(" + provincia + ")' \n";
    }

}
