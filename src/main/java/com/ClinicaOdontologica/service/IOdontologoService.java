package com.ClinicaOdontologica.service;

import com.ClinicaOdontologica.model.dto.OdontologoDTO;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface IOdontologoService extends ICRUDService<OdontologoDTO> {

    Set<OdontologoDTO> getOdontologoByLastNameLike(String lastname);
}
