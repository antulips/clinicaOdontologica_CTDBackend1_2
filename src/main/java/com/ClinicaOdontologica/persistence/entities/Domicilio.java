package com.ClinicaOdontologica.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "domicilios")
@Getter @Setter
public class Domicilio {
    @Id
    @SequenceGenerator(name = "domicilio_sequence", sequenceName = "domicilio_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "domicilio_sequence")
    @Column(name = "id")
    private Long id;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;

    public Domicilio() {
    }
}
