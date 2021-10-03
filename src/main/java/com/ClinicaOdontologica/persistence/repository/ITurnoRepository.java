package com.ClinicaOdontologica.persistence.repository;

import com.ClinicaOdontologica.persistence.entities.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ITurnoRepository extends JpaRepository<Turno, Long> {

}
