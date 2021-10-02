package com.ClinicaOdontologica.controller.impl;

import com.ClinicaOdontologica.controller.ICRUDController;
import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.model.dto.TurnoDTO;
import com.ClinicaOdontologica.service.ITurnoService;
import com.ClinicaOdontologica.util.Messages;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class TurnoController implements ICRUDController<TurnoDTO> {

    private final static Logger logger = Logger.getLogger(OdontologoController.class);

    @Autowired
    private ITurnoService turnoService;

    @Override
    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody TurnoDTO newTurno) throws ServiceException, ResourceNotFoundException {
        logger.info("Ingresando el siguiente turno a la base de datos: \n" + newTurno.toString());

        Optional<TurnoDTO> turno = Optional.of(turnoService.create(newTurno));

        logger.info(String.format(Messages.CREADO_CON_EXITO, "turno", turno.get().getId()));

        return ResponseEntity.ok(String.format(Messages.CREADO_CON_EXITO, "turno", turno.get().getId()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Buscando en la base de datos el turno con el id: " + id);

        Optional<TurnoDTO> response = turnoService.readById(id);

        logger.info("Se encontró el turno:\n" + response.get());

        return ResponseEntity.ok(response.get());
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody TurnoDTO turno) throws ResourceNotFoundException, ServiceException {
        logger.info("Actualizando en la base de datos el turno con el id: " + turno.getId());

        Optional<TurnoDTO> response = Optional.ofNullable(turnoService.update(turno));

        logger.info("Se actualizó el turno:\n" + response.get().getId());

        return ResponseEntity.ok(response.get());
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Borrando de la base de datos el turno con el id: " + id);

        Optional<TurnoDTO> response = turnoService.readById(id);

        logger.info("Se eliminó el turno:\n" + response.get().getId());

        return ResponseEntity.ok(turnoService.delete(id));
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        logger.info("Buscando todos los turnos de la base de datos.");

        Collection<TurnoDTO> listaDeTurnos = turnoService.getAll();

        logger.info(String.format("Se encontraron %s turnos.", listaDeTurnos.size()));

        return ResponseEntity.ok(listaDeTurnos);
    }

    /*@GetMapping("/proximos")
    public ResponseEntity<List<TurnoDto>> buscarTurnosDesde(
            @RequestParam Integer dia,
            @RequestParam Integer mes,
            @RequestParam Integer anio,
            @RequestParam(defaultValue = "0") Integer hora,
            @RequestParam(defaultValue = "0") Integer minuto,
            @RequestParam(defaultValue = "7") Integer cantidadDias) {
        LocalDateTime desde = LocalDateTime.of(anio, mes, dia, hora, minuto);
        List<TurnoDto> turnos = turnoService.consultarProximosTurnos(desde, cantidadDias);
        return ResponseEntity.ok(turnos);
    }*/
}
