package com.ClinicaOdontologica.controller.impl;

import com.ClinicaOdontologica.controller.ICRUDController;
import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.model.dto.PacienteDTO;
import com.ClinicaOdontologica.service.impl.PacienteServiceImpl;

import com.ClinicaOdontologica.util.Messages;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController implements ICRUDController<PacienteDTO> {

    private final static Logger logger = Logger.getLogger(PacienteController.class);

    @Autowired
    private PacienteServiceImpl pacienteService;

    @Override
    @ApiOperation(value = "ESP: Ingresa un nuevo Paciente en la base de datos.\nEN: Creates a new Paciente in the database.")
    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody PacienteDTO newPaciente) throws ServiceException {
        logger.info("Ingresando el siguiente paciente a la base de datos: \n" + newPaciente.toString());

        Optional<PacienteDTO> paciente = Optional.of(pacienteService.create(newPaciente));

        logger.info(String.format(Messages.CREADO_CON_EXITO, "paciente", paciente.get().getId()));

        return ResponseEntity.ok(String.format(Messages.CREADO_CON_EXITO, "paciente", paciente.get().getId()));
    }

    @Override
    @ApiOperation(value = "ESP: Obtiene un Paciente con Domicilio por id.\nEN: Returns a Paciente with Domicilio by id.")
    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Buscando en la base de datos el paciente con el id: " + id);

        Optional<PacienteDTO> response = pacienteService.readById(id);

        if (response.isPresent()) {
            logger.info("Se encontró el paciente:\n" + response.get());
        }

        return ResponseEntity.ok(response.get());
    }

    @Override
    @ApiOperation(value = "ESP: Actualiza por id un Paciente registrado.\nEN: Updates a Paciente by id.")
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody PacienteDTO paciente) throws ResourceNotFoundException, ServiceException {
        logger.info("Actualizando en la base de datos el paciente con el id: " + paciente.getId());

        Optional<PacienteDTO> response = Optional.ofNullable(pacienteService.update(paciente));

        if (response.isPresent()) {
            logger.info("Se actualizó el paciente:\n" + response.get().getId());
        }

        return ResponseEntity.ok(response.get());
    }

    @Override
    @ApiOperation(value = "ESP: Elimina un Paciente por id.\nEN: Deletes a Paciente by id.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Borrando de la base de datos el paciente con el id: " + id);

        Optional<PacienteDTO> response = pacienteService.readById(id);

        if (response.isPresent()) {
            logger.info("Se eliminó el paciente:\n" + response.get().getId());
        }

        return ResponseEntity.ok(pacienteService.delete(id));

    }

    @Override
    @ApiOperation(value = "ESP: Obtiene todos los Pacientes registrados.\nEN: Gets all Pacientes from the database.")
    @GetMapping("/all")
    public ResponseEntity<Collection<PacienteDTO>> getAll(){
        logger.info("Buscando todos los pacientes de la base de datos.");

        Collection<PacienteDTO> listaDePacientes = pacienteService.getAll();

        logger.info(String.format("Se encontraron %s pacientes.", listaDePacientes.size()));

        return ResponseEntity.ok(listaDePacientes);
    }

}
