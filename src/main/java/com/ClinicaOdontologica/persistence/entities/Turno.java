package com.ClinicaOdontologica.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="Turnos")
@Getter @Setter
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "turno_seq")
    @SequenceGenerator(name = "turno_seq", sequenceName = "turno_seq", allocationSize = 1)
    @Column(name = "turno_id")
    private Long id;

    private LocalDate fecha;
    private LocalTime hora;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name="paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name="odontologo_id", nullable = false)
    private Odontologo odontologo;

    public Turno() {
    }
}
