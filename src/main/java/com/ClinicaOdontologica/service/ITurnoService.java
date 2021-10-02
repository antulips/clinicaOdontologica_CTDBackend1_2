package com.ClinicaOdontologica.service;

import com.ClinicaOdontologica.model.dto.TurnoDTO;

import java.time.LocalDate;
import java.util.Collection;

public interface ITurnoService extends ICRUDService<TurnoDTO>{
    Collection<TurnoDTO> getTurnosForOneWeek(LocalDate fromDate);

    Collection<TurnoDTO> getTurnosForPeriod(LocalDate from, LocalDate to);
}
