package com.ClinicaOdontologica.controller;

import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.model.dto.OdontologoDTO;
import com.ClinicaOdontologica.service.IOdontologoService;
import com.ClinicaOdontologica.service.impl.IOdontologoServiceImpl;
import com.ClinicaOdontologica.util.Messages;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/odontologos")
public class OdontologoController implements ICRUDController<OdontologoDTO> {

    private final static Logger logger = Logger.getLogger(OdontologoController.class);

    @Autowired
    private IOdontologoService odontologoService;

    @Override
    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody OdontologoDTO newOdondologo) throws ServiceException, ResourceNotFoundException {
        logger.info("Ingresando el siguiente odontólogo a la base de datos: \n" + newOdondologo.toString());

        Optional<OdontologoDTO> odontologo = Optional.of(odontologoService.create(newOdondologo));

                   /*CÓDIGO PARA EL TURNO CONTROLLER
            if(!odontologo.equals(Messages.ERROR_ODONTOLOGO_PACIENTE_NO_EXISTEN)) {
                return ResponseEntity.ok(String.format(Messages.TURNO_CREADO_CON_EXITO, odontologo));
            }else{
                response = ResponseEntity.badRequest().body(Messages.ERROR_AL_CREAR_TURNO + " " + Messages.ERROR_ODONTOLOGO_PACIENTE_NO_EXISTEN);
            }*/

        logger.info(String.format(Messages.CREADO_CON_EXITO, "odontólogo", odontologo.get().getId()));

        return ResponseEntity.ok(String.format(Messages.CREADO_CON_EXITO, "odontólogo", odontologo.get().getId()));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Buscando en la base de datos el odontólogo con el id: " + id);

        Optional<OdontologoDTO> response = odontologoService.readById(id);

        logger.info("Se encontró el odontólogo:\n" + response.get().toString());

        return ResponseEntity.ok(response.get());
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody OdontologoDTO odontologo) throws ResourceNotFoundException, ServiceException {
        logger.info("Actualizando en la base de datos el odontólogo con el id: " + odontologo.getId());

        Optional<OdontologoDTO> response = Optional.ofNullable(odontologoService.update(odontologo));

        logger.info("Se actualizó el odontólogo:\n" + response.get().getId());

        return ResponseEntity.ok(response.get());

    }

    @Override
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException {
        logger.info("Borrando de la base de datos el odontólogo con el id: " + id);

        Optional<OdontologoDTO> response = odontologoService.readById(id);

        logger.info("Se eliminó el odontólogo:\n" + response.get().toString());

        return ResponseEntity.ok(odontologoService.delete(id));
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<Collection<OdontologoDTO>> getAll(){
        logger.info("Buscando todos los odontólogos de la base de datos.");

        Collection<OdontologoDTO> listaDeOdontologos = odontologoService.getAll();

        logger.info(String.format("Se encontraron %s odontólogos.", listaDeOdontologos.size()));

        return ResponseEntity.ok(listaDeOdontologos);
    }

    @GetMapping("/search/{lastname}")
    public Set<OdontologoDTO> listOdontologos(@RequestParam String lastname){
        return ((IOdontologoServiceImpl)odontologoService).getOdontologoByLastNameLike(lastname);
    }

}
