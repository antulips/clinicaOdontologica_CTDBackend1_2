package com.ClinicaOdontologica.service.impl;

import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.model.dto.TurnoDTO;
import com.ClinicaOdontologica.persistence.entities.Turno;
import com.ClinicaOdontologica.persistence.repository.ITurnoRepository;
import com.ClinicaOdontologica.service.ITurnoService;
import com.ClinicaOdontologica.util.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ITurnoServiceImpl implements ITurnoService {

    @Autowired
    private ITurnoRepository turnoRepository;

    @Autowired
    private IPacienteServiceImpl pacienteService;

    @Autowired IOdontologoServiceImpl odontologoService;

    @Autowired
    ObjectMapper mapper;

    @Override
    public TurnoDTO create(TurnoDTO turno) throws ServiceException, ResourceNotFoundException {
        if (turno == null || turno.getPaciente().getId() == null || turno.getOdontologo().getId() == null) {
            throw new ServiceException(String.format(Messages.ERROR_AL_CREAR, "turno"));
        } else if (validatePacienteAndOdontologo(turno.getPaciente().getId(), turno.getOdontologo().getId())){
            TurnoDTO newTurno = mapper.convertValue(saveTurno(turno), TurnoDTO.class);
            turno.setId(newTurno.getId());
            turno.setPaciente(newTurno.getPaciente());
            turno.setOdontologo(newTurno.getOdontologo());
        } else {
            throw new ServiceException(String.format(Messages.ERROR_CAUSA_DESCONOCIDA));
        }
        return turno;
    }

    @Override
    public Optional<TurnoDTO> readById(Long id) throws ResourceNotFoundException {
        Optional<Turno> response = turnoRepository.findById(id);

        if (response.isEmpty()){
            throw new ResourceNotFoundException(String.format(Messages.ERROR_NO_EXISTE, "turno", id));
        }

        return Optional.of(mapper.convertValue(response, TurnoDTO.class));
    }

    @Override
    public TurnoDTO update(TurnoDTO turno) throws ResourceNotFoundException, ServiceException {
        if (turno == null || turno.getId() == null) {
            throw new ServiceException("Debe proporcionar un id para actualizar un turno.");
        }

        Optional<Turno> response = turnoRepository.findById(turno.getId());

        if (response.isEmpty()){
            throw new ResourceNotFoundException("No se puede actualizar. " + String.format(Messages.ERROR_NO_EXISTE, "turno", turno.getId()));
        }

        //Hago que deje de ser un Optional y lo convierto en DTO para no trabajar con Entity desde el controller
        TurnoDTO newTurno = mapper.convertValue(response.get(), TurnoDTO.class);

       // newPaciente.setApellido(Optional.ofNullable(turno.getApellido()).orElse(newPaciente.getApellido()));
       // newPaciente.setDni(Optional.ofNullable(turno.getDni()).orElse(newPaciente.getDni()));
       // newPaciente.setFechaIngreso(Optional.ofNullable(turno.getFechaIngreso()).orElse(newPaciente.getFechaIngreso()));

        return mapper.convertValue(saveTurno(newTurno), TurnoDTO.class);
    }

    @Override
    public String delete(Long id) throws ResourceNotFoundException {
        Optional<Turno> response = Optional.of(turnoRepository.getById(id));

        if (response.isEmpty()){
            throw new ResourceNotFoundException("No se puede eliminar. " + String.format(Messages.ERROR_NO_EXISTE, "turno", id));
        }

        turnoRepository.deleteById(id);
        return ("El siguiente turno fue eliminado con éxito:\n" + mapper.convertValue(response.get(), TurnoDTO.class).toString());
    }

    @Override
    public Collection<TurnoDTO> getAll() {
        List<Turno> turnoList = turnoRepository.findAll();
        Set<TurnoDTO> response = new HashSet();

        for (Turno turno: turnoList) {
            response.add(mapper.convertValue(turno, TurnoDTO.class));
        }
        return response;
    }

    private Turno saveTurno(TurnoDTO turno) {
        Turno newTurno = mapper.convertValue(turno, Turno.class);
        return turnoRepository.save(newTurno);
    }

    private boolean validatePacienteAndOdontologo(Long idPaciente, Long idOdontologo) throws ResourceNotFoundException {
        return pacienteService.readById(idPaciente).isPresent() && odontologoService.readById(idOdontologo).isPresent();
    }
}
