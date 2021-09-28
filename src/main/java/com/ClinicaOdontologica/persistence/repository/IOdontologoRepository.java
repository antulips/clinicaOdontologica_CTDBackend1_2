package com.ClinicaOdontologica.persistence.repository;

import com.ClinicaOdontologica.persistence.entities.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IOdontologoRepository extends JpaRepository<Odontologo, Long> {

    @Query("from Odontologo o where o.apellido like %:lastname%")
    Set<Odontologo> getOdontologoByLastNameLike(@Param("lastname") String lastname);

}
