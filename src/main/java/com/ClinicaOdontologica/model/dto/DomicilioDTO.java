package com.ClinicaOdontologica.model.dto;

import lombok.Data;

@Data
public class DomicilioDTO {

    private Long id;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;

}
