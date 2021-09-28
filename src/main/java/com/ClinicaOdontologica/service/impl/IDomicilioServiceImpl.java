/*package com.ClinicaOdontologica.service.impl;

import com.ClinicaOdontologica.model.dto.DomicilioDTO;
import com.ClinicaOdontologica.persistence.entities.Domicilio;
import com.ClinicaOdontologica.persistence.repository.IDomicilioRepository;
import com.ClinicaOdontologica.service.IDomicilioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class IDomicilioServiceImpl implements IDomicilioService {

    @Autowired
    private IDomicilioRepository domicilioRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public DomicilioDTO create(DomicilioDTO domicilio) {
        if (domicilio != null) {
            domicilio.setId(saveDomicilio(domicilio).getId());
        }
        return domicilio;
    }

    @Override
    public Optional<DomicilioDTO> readById(Long id) {
        Optional<Domicilio> found = domicilioRepository.findById(id);
        return Optional.of(mapper.convertValue(found, DomicilioDTO.class));
    }

       @Override
    public DomicilioDTO update(DomicilioDTO domicilio) {
        saveDomicilio(domicilio);
        return domicilio;
    }

    //TODO: REVISAR QUÉ ES NECESARIO IMPLEMENTAR PARA DOMICILIO
    @Override
    public String delete(Long id) {
        return null;
    }

    @Override
    public Collection<DomicilioDTO> getAll() {
        return null;
    }

    private Domicilio saveDomicilio(DomicilioDTO domicilio) {
        Domicilio newDomicilio = mapper.convertValue(domicilio, Domicilio.class);
        return domicilioRepository.save(newDomicilio);
    }
}
*/