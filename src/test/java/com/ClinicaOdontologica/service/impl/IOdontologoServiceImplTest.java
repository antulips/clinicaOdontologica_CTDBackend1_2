package com.ClinicaOdontologica.service.impl;

import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.model.dto.OdontologoDTO;
import com.ClinicaOdontologica.service.IOdontologoService;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class IOdontologoServiceImplTest {

    @Autowired
    private IOdontologoService odontologoService;

    OdontologoDTO o;
    OdontologoDTO o2;
    OdontologoDTO o3;

    @BeforeEach
    public void cargarDatos() throws ServiceException, ResourceNotFoundException {
        o = new OdontologoDTO();
        o.setApellido("Tester");
        o.setNombre("Doctor o");
        o.setMatricula("FKH ");

        o2 = new OdontologoDTO();
        o2.setApellido("Retester");
        o2.setNombre("Doctor 2");
        o2.setMatricula("FIU 6548/7");
        o2.setId(odontologoService.create(o2).getId());

        o3 = new OdontologoDTO();
        o3.setApellido("Testeo");
        o3.setNombre("Doctor Cito");
        o3.setMatricula("HOW 7841/1");
        odontologoService.create(o3);
        o3.setId(odontologoService.create(o3).getId());
    }

    @Test
    public void test01DebeCrearElOdontologo() throws ServiceException, ResourceNotFoundException {
        //DADO>@BeforeAll

        //CUANDO
        Object creado = odontologoService.create(o);

        //ENTONCES
        assertEquals(creado, o);
    }

    @Test
    public void test02NoDebeCrearElOdontologo() throws ServiceException {
        //DADO
        OdontologoDTO noO = null;

        //ENTONCES > CUANDO
        assertThrows(ServiceException.class, () -> odontologoService.create(noO));
    }

    @Test
    public void test03DebeTraerElOdontologoBuscado() throws ServiceException, ResourceNotFoundException {
        //DADO>@BeforeAll

        //CUANDO
        OdontologoDTO respuesta = odontologoService.readById(o2.getId()).get();

        //ENTONCES
        assertEquals(respuesta, o2);
    }

    @Test
    public void test04DebeActualizarElOdontologo() throws ServiceException, ResourceNotFoundException {
        //DADO
        o2.setNombre("Doc Doc");

        //CUANDO
        odontologoService.update(o2);
        Object respuesta = odontologoService.readById(o2.getId());

        //ENTONCES
        assertEquals(o2.getNombre(), "Doc Doc");
    }

    @Test
    public void test05NoDebeActualizarElOdontologoSinId() throws ServiceException, ResourceNotFoundException {
        //DADO
        o.setNombre("Doc Doc");

        //ENTONCES > CUANDO
        assertThrows(ServiceException.class, () -> odontologoService.update(o));
    }

    @Test
    public void test06DebeEliminarOdontologoPorId() throws ResourceNotFoundException {
        //DADO
        Long idOdontologoParaBorrar = odontologoService.readById(o3.getId()).get().getId();

        //CUANDO
        odontologoService.delete(idOdontologoParaBorrar);

        //ENTONCES
        assertThrows(ResourceNotFoundException.class, () -> odontologoService.readById(idOdontologoParaBorrar));
    }

    @Test
    public void test07DebeTraerTodosLosOdontologos() {
        //DADO>@BeforeAll

        //CUANDO
        Collection<OdontologoDTO> odontologos = odontologoService.getAll();

        //ENTONCES
        //assertFalse(odontologos.isEmpty());
        assertEquals(4, odontologos.size());

    }

}