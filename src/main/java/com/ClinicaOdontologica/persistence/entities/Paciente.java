package com.ClinicaOdontologica.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "pacientes")
@Getter @Setter
public class Paciente {

    @Id
    @SequenceGenerator(name = "paciente_sequence", sequenceName = "paciente_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paciente_sequence")
    private long id;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaIngreso;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id_domicilio", referencedColumnName = "id")
    private Domicilio domicilio;

    @OneToMany(mappedBy = "paciente", fetch=FetchType.EAGER)
    @JsonIgnore
    private Set<Turno> turnos;

    public Paciente() {
    }
}
