package com.ClinicaOdontologica.controller.impl;

import com.ClinicaOdontologica.controller.ICRUDController;
import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.model.dto.TurnoDTO;
import com.ClinicaOdontologica.service.ITurnoService;
import com.ClinicaOdontologica.util.Messages;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController implements ICRUDController<TurnoDTO> {

    private final static Logger logger = Logger.getLogger(OdontologoController.class);

    @Autowired
    private ITurnoService turnoService;

    @Override
    @ApiOperation(value = "ESP: Ingresa un nuevo Turno en la base de datos.\nEN: Creates a new Turno in the database.")
    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody TurnoDTO newTurno) throws ServiceException, ResourceNotFoundException {
        logger.info("Ingresando el siguiente turno a la base de datos: \n" + newTurno.toString());

        Optional<TurnoDTO> turno = Optional.of(turnoService.create(newTurno));

        logger.info(String.format(Messages.CREADO_CON_EXITO, "turno", turno.get().getId()));

        return ResponseEntity.ok(String.format(Messages.CREADO_CON_EXITO, "turno", turno.get().getId()));
    }

    @Override
    @ApiOperation(value = "ESP: Obtiene un Turno, con Paciente y Odontologo, por id.\nEN: Returns a Turno with Paciente and Odontologo by id.")
    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Buscando en la base de datos el turno con el id: " + id);

        Optional<TurnoDTO> response = turnoService.readById(id);

        logger.info("Se encontró el turno:\n" + response.get());

        return ResponseEntity.ok(response.get());
    }

    @Override
    @ApiOperation(value = "ESP: Actualiza por id un Turno registrado.\nEN: Updates a Turno by id.")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody TurnoDTO turno) throws ResourceNotFoundException, ServiceException {
        logger.info("Actualizando en la base de datos el turno con el id: " + turno.getId());

        Optional<TurnoDTO> response = Optional.ofNullable(turnoService.update(turno));

        logger.info("Se actualizó el turno:\n" + response.get().getId());

        return ResponseEntity.ok(response.get());
    }

    @Override
    @ApiOperation(value = "ESP: Elimina un Turno por id.\nEN: Deletes a Turno by id.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Borrando de la base de datos el turno con el id: " + id);

        Optional<TurnoDTO> response = turnoService.readById(id);

        logger.info("Se eliminó el turno:\n" + response.get().getId());

        return ResponseEntity.ok(turnoService.delete(id));
    }

    @Override
    @ApiOperation(value = "ESP: Obtiene todos los Turnos registrados.\nEN: Gets all Turnos from the database.")
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        logger.info("Buscando todos los turnos de la base de datos.");

        Collection<TurnoDTO> listaDeTurnos = turnoService.getAll();

        logger.info(String.format("Se encontraron %s turnos.", listaDeTurnos.size()));

        return ResponseEntity.ok(listaDeTurnos);
    }

    @ApiOperation(value = "ESP: Obtiene los Turnos registrados durante los 7 días posteriores a la fecha ingresada.\nEN: Gets all Turnos in a week from the date given.")
    @GetMapping("/weekly")
    public ResponseEntity<?> getAllForAWeek(
            @RequestParam Integer day,
            @RequestParam Integer month,
            @RequestParam Integer year
    ) {
        LocalDate fromDate = LocalDate.of(year, month, day);

        logger.info("Buscando los turnos para la semana del " + fromDate + ".");

        Collection<TurnoDTO> listaDeTurnos = turnoService.getTurnosForOneWeek(fromDate);

        logger.info(String.format("Se encontraron %s turnos.", listaDeTurnos.size()));

        return ResponseEntity.ok(listaDeTurnos);
    }

    @ApiOperation(value = "ESP: Obtiene los Turnos registrados entre la fechas ingresadas.\nEN: Gets all Turnos the period in between the given dates.")
    @GetMapping("/period")
    public ResponseEntity<?> getAllFromTo(
            @RequestParam Integer dayFrom,
            @RequestParam Integer monthFrom,
            @RequestParam Integer yearFrom,
            @RequestParam Integer dayTo,
            @RequestParam Integer monthTo,
            @RequestParam Integer yearTo
    ) {
        LocalDate fromDate = LocalDate.of(yearFrom, monthFrom, dayFrom);
        LocalDate toDate = LocalDate.of(yearTo, monthTo, dayTo);

        logger.info("Buscando los turnos para la semana del " + fromDate + ".");

        Collection<TurnoDTO> listaDeTurnos = turnoService.getTurnosForPeriod(fromDate, toDate);

        logger.info(String.format("Se encontraron %s turnos.", listaDeTurnos.size()));

        return ResponseEntity.ok(listaDeTurnos);
    }

}
