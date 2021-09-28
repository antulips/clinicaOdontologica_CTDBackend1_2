package com.ClinicaOdontologica.service.impl;

import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.model.dto.OdontologoDTO;
import com.ClinicaOdontologica.persistence.entities.Odontologo;
import com.ClinicaOdontologica.persistence.repository.IOdontologoRepository;
import com.ClinicaOdontologica.service.IOdontologoService;
import com.ClinicaOdontologica.util.Messages;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IOdontologoServiceImpl implements IOdontologoService {

    @Autowired
    private IOdontologoRepository odontologoRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public OdontologoDTO create(OdontologoDTO odontologo) throws ServiceException {
        if (odontologo != null){
            odontologo.setId(saveOdontologo(odontologo).getId());
        } else {
            throw new ServiceException(String.format(Messages.ERROR_AL_CREAR, "odontologo"));
        }
        return odontologo;
    }

    @Override
    public Optional<OdontologoDTO> readById(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> response = odontologoRepository.findById(id);

        if (response.isEmpty()){
            throw new ResourceNotFoundException(String.format(Messages.ERROR_NO_EXISTE, "odontólogo", id));
        }

        return Optional.of(mapper.convertValue(response, OdontologoDTO.class));
    }

    @Override
    public OdontologoDTO update(OdontologoDTO odontologo) throws ResourceNotFoundException, ServiceException {
        if (odontologo == null || odontologo.getId() == null) {
            throw new ServiceException("Debe proporcionar un id para actualizar un odontólogo.");
        }

        Optional<Odontologo> response = odontologoRepository.findById(odontologo.getId());

        if (response.isEmpty()){
            throw new ResourceNotFoundException("No se puede actualizar. " + String.format(Messages.ERROR_NO_EXISTE, "odontólogo", odontologo.getId()));
        }

        saveOdontologo(odontologo);
        return odontologo;
    }

    @Override
    public String delete(Long id) throws ResourceNotFoundException {
        Optional<Odontologo> response = odontologoRepository.findById(id);

        if (response.isEmpty()){
            throw new ResourceNotFoundException("No se puede eliminar. " + String.format(Messages.ERROR_NO_EXISTE, "odontólogo", id));
        }

        odontologoRepository.deleteById(id);
        return ("El siguiente odontologo fue eliminado con éxito:\n" + mapper.convertValue(response.get(), OdontologoDTO.class).toString());
    }

    @Override
    public Collection<OdontologoDTO> getAll() {
        Set<OdontologoDTO> response = new HashSet();
        List<Odontologo> odontologoList = odontologoRepository.findAll();

        for (Odontologo odontologo: odontologoList) {
            response.add(mapper.convertValue(odontologo, OdontologoDTO.class));
        }
        return response;
    }

    private Odontologo saveOdontologo(OdontologoDTO odontologo) {
        Odontologo newOdontologo = mapper.convertValue(odontologo, Odontologo.class);
        return odontologoRepository.save(newOdontologo);
    }

    public Set<OdontologoDTO> getOdontologoByLastNameLike(String lastname){
        Set<Odontologo> odontologoList = odontologoRepository.getOdontologoByLastNameLike(lastname);

        Set<OdontologoDTO> response = new HashSet();

        for (Odontologo odontologo: odontologoList) {
            response.add(mapper.convertValue(odontologo, OdontologoDTO.class));
        }
        return response;
    }
}
