package com.ClinicaOdontologica.controller;

import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ICRUDController<T> {
    ResponseEntity<?> create(@RequestBody T t) throws ServiceException, ResourceNotFoundException;
    ResponseEntity<?> readById(@PathVariable Long id) throws ResourceNotFoundException;
    ResponseEntity<?> update(@RequestBody T t) throws ResourceNotFoundException, ServiceException;
    ResponseEntity<String> delete(@PathVariable Long id) throws ResourceNotFoundException;
    ResponseEntity<?> getAll();
}
