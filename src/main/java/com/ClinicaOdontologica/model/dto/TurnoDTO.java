package com.ClinicaOdontologica.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TurnoDTO {

    private Long id;
    private LocalDate fecha;
    private LocalTime hora;
    private PacienteDTO paciente;
    private OdontologoDTO odontologo;

}
