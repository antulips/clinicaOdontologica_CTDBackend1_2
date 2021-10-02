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

    @Override
    public String toString() {
        return "Turno:" +
                "\n\tid: '" + id + "'" +
                "\n\tFecha: '" + fecha + "'" +
                "\n\tHora: '" + hora + "'" +
                "\n\tOdontólogo: '" + odontologo.toString() + "'" +
                "\n\tPaciente: '" + paciente.toString() + "' \n";
    }
}
