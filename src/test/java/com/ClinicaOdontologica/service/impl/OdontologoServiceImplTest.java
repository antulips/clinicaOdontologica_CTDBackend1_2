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
public class OdontologoServiceImplTest {

    @Autowired
    private IOdontologoService odontologoService;

    OdontologoDTO dummyOd;
    OdontologoDTO dummyOd2;
    OdontologoDTO dummyOd3;

    @BeforeEach
    public void cargarDatos() throws ServiceException, ResourceNotFoundException {
        dummyOd = new OdontologoDTO();
        dummyOd.setApellido("Tester");
        dummyOd.setNombre("Doctor o");
        dummyOd.setMatricula("FKH ");

        dummyOd2 = new OdontologoDTO();
        dummyOd2.setApellido("Retester");
        dummyOd2.setNombre("Doctor 2");
        dummyOd2.setMatricula("FIU 6548/7");
        dummyOd2.setId(odontologoService.create(dummyOd2).getId());

        dummyOd3 = new OdontologoDTO();
        dummyOd3.setApellido("Tester");
        dummyOd3.setNombre("Doctor Cito");
        dummyOd3.setMatricula("HOW 7841/1");
        odontologoService.create(dummyOd3);
        dummyOd3.setId(odontologoService.create(dummyOd3).getId());
    }

    @Test
    public void test01DebeCrearElOdontologo() throws ServiceException, ResourceNotFoundException {
        //DADO>@BeforeAll

        //CUANDO
        Object creado = odontologoService.create(dummyOd);

        //ENTONCES
        assertEquals(creado, dummyOd);
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
        OdontologoDTO respuesta = odontologoService.readById(dummyOd2.getId()).get();

        //ENTONCES
        assertEquals(respuesta, dummyOd2);
    }

    @Test
    public void test04DebeActualizarElOdontologo() throws ServiceException, ResourceNotFoundException {
        //DADO
        dummyOd2.setNombre("Doc Doc");

        //CUANDO
        odontologoService.update(dummyOd2);
        Object respuesta = odontologoService.readById(dummyOd2.getId());

        //ENTONCES
        assertEquals(dummyOd2.getNombre(), "Doc Doc");
    }

    @Test
    public void test05NoDebeActualizarElOdontologoSinId() throws ServiceException, ResourceNotFoundException {
        //DADO
        dummyOd.setNombre("Doc Doc");

        //ENTONCES > CUANDO
        assertThrows(ServiceException.class, () -> odontologoService.update(dummyOd));
    }

    @Test
    public void test06DebeEliminarOdontologoPorId() throws ResourceNotFoundException {
        //DADO
        Long idOdontologoParaBorrar = odontologoService.readById(dummyOd3.getId()).get().getId();

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
        assertFalse(odontologos.isEmpty());
        assertEquals(4, odontologos.size());
    }

    @Test
    public void test07DebeTraerTodosLosOdontologosConUnApellidoSimilarA() {
        //DADO>@BeforeAll
        String lastnameLike = "Tester";

        //CUANDO
        Collection<OdontologoDTO> odontologos = ((OdontologoServiceImpl)odontologoService).getOdontologoByLastNameLike(lastnameLike);

        //ENTONCES
        assertEquals(2, odontologos.size());
    }

}