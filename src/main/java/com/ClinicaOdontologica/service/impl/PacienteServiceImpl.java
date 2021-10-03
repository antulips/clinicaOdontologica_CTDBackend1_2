package com.ClinicaOdontologica.service.impl;

import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.model.dto.PacienteDTO;
import com.ClinicaOdontologica.persistence.entities.Paciente;
import com.ClinicaOdontologica.persistence.repository.IPacienteRepository;
import com.ClinicaOdontologica.service.IPacienteService;
import com.ClinicaOdontologica.util.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class PacienteServiceImpl implements IPacienteService {

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public PacienteDTO create(PacienteDTO paciente) throws ServiceException {
        if (paciente != null) {

            //seteo la fecha
            paciente.setFechaIngreso(LocalDate.now());
            //Guardo el paciente y lo almaceno para actualiza el DTO
            PacienteDTO newPaciente = mapper.convertValue(savePaciente(paciente), PacienteDTO.class);
            paciente.setId(newPaciente.getId());
            paciente.setDomicilio(newPaciente.getDomicilio());
        } else {
            throw new ServiceException(String.format(Messages.ERROR_AL_CREAR, "paciente"));
        }
        return paciente;
    }

    @Override
    public Optional<PacienteDTO> readById(Long id) throws ResourceNotFoundException {
        Optional<Paciente> response = pacienteRepository.findById(id);

        if (response.isEmpty()){
            throw new ResourceNotFoundException(String.format(Messages.ERROR_NO_EXISTE, "paciente", id));
        }

        return Optional.of(mapper.convertValue(response, PacienteDTO.class));
    }

    @Override
    public PacienteDTO update(PacienteDTO paciente) throws ResourceNotFoundException, ServiceException {
        if (paciente == null || paciente.getId() == null) {
            throw new ServiceException("Debe proporcionar un id para actualizar un paciente.");
        }

        Optional<Paciente> response = pacienteRepository.findById(paciente.getId());

        if (response.isEmpty()){
            throw new ResourceNotFoundException("No se puede actualizar. " + String.format(Messages.ERROR_NO_EXISTE, "paciente", paciente.getId()));
        }

        //Hago que deje de ser un Optional y lo convierto en DTO para no trabajar con Entity desde el controller
        PacienteDTO newPaciente = mapper.convertValue(response.get(), PacienteDTO.class);

        newPaciente.setNombre(Optional.ofNullable(paciente.getNombre()).orElse(newPaciente.getNombre()));
        newPaciente.setApellido(Optional.ofNullable(paciente.getApellido()).orElse(newPaciente.getApellido()));
        newPaciente.setDni(Optional.ofNullable(paciente.getDni()).orElse(newPaciente.getDni()));
        newPaciente.setFechaIngreso(Optional.ofNullable(paciente.getFechaIngreso()).orElse(newPaciente.getFechaIngreso()));

        return mapper.convertValue(savePaciente(newPaciente), PacienteDTO.class);
    }

    @Override
    public String delete(Long id) throws ResourceNotFoundException {
        Optional<Paciente> response = Optional.of(pacienteRepository.getById(id));

        if (response.isEmpty()){
            throw new ResourceNotFoundException("No se puede eliminar. " + String.format(Messages.ERROR_NO_EXISTE, "paciente", id));
        }

        pacienteRepository.deleteById(id);

        return ("El siguiente paciente fue eliminado con éxito:\n" + mapper.convertValue(response.get(), PacienteDTO.class).toString());
    }

    @Override
    public Collection<PacienteDTO> getAll() {
        List<Paciente> pacienteList = pacienteRepository.findAll();
        Set<PacienteDTO> response = new HashSet();

        for (Paciente paciente: pacienteList) {
            response.add(mapper.convertValue(paciente, PacienteDTO.class));
        }
        return response;
    }

    private Paciente savePaciente(PacienteDTO paciente) {
        Paciente newPaciente = mapper.convertValue(paciente, Paciente.class);
        return pacienteRepository.save(newPaciente);
    }
}
