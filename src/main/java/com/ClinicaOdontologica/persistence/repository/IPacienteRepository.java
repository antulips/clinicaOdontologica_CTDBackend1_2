package com.ClinicaOdontologica.persistence.repository;

import com.ClinicaOdontologica.persistence.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface IPacienteRepository extends JpaRepository<Paciente, Long> {
}
