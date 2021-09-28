package com.ClinicaOdontologica.controller;

import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.model.dto.PacienteDTO;
import com.ClinicaOdontologica.service.impl.IPacienteServiceImpl;

import com.ClinicaOdontologica.util.Messages;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/pacientes")
public class PacienteController implements ICRUDController<PacienteDTO>{

    private final static Logger logger = Logger.getLogger(PacienteController.class);

    @Autowired
    private IPacienteServiceImpl pacienteService;

    @Override
    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody PacienteDTO newPaciente) throws ServiceException {
        logger.info("Ingresando el siguiente paciente a la base de datos: \n" + newPaciente.toString());

        Optional<PacienteDTO> paciente = Optional.of(pacienteService.create(newPaciente));

        logger.info(String.format(Messages.CREADO_CON_EXITO, "paciente", paciente.get().getId()));

        return ResponseEntity.ok(String.format(Messages.CREADO_CON_EXITO, "paciente", paciente.get().getId()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Buscando en la base de datos el paciente con el id: " + id);

        Optional<PacienteDTO> response = pacienteService.readById(id);

        logger.info("Se encontró el paciente:\n" + response.get().toString());

        return ResponseEntity.ok(response.get());
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody PacienteDTO paciente) throws ResourceNotFoundException, ServiceException {
        logger.info("Actualizando en la base de datos el paciente con el id: " + paciente.getId());

        Optional<PacienteDTO> response = Optional.ofNullable(pacienteService.update(paciente));

        logger.info("Se actualizó el paciente:\n" + response.get().getId());

        return ResponseEntity.ok(response.get());
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Borrando de la base de datos el paciente con el id: " + id);

        Optional<PacienteDTO> response = pacienteService.readById(id);

        logger.info("Se eliminó el paciente:\n" + response.get().getId());

        return ResponseEntity.ok(String.format(pacienteService.delete(id)));

    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<Collection<PacienteDTO>> getAll(){
        logger.info("Buscando todos los pacientes de la base de datos.");

        Collection<PacienteDTO> listaDePacientes = pacienteService.getAll();

        logger.info(String.format("Se encontraron %s pacientes.", listaDePacientes.size()));

        return ResponseEntity.ok(listaDePacientes);
    }

}
