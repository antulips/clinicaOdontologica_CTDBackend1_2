package com.ClinicaOdontologica.service;

import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface ICRUDService<T> {
    T create(T t) throws ServiceException, ResourceNotFoundException;
    Optional<T> readById(Long id) throws ResourceNotFoundException;
    T update(T t) throws ResourceNotFoundException, ServiceException;
    String delete(Long id) throws ResourceNotFoundException;

    Collection<T> getAll();
}
