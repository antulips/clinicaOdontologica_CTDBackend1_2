package com.ClinicaOdontologica.service.impl;

import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.model.dto.DomicilioDTO;
import com.ClinicaOdontologica.model.dto.PacienteDTO;
import com.ClinicaOdontologica.service.IPacienteService;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class PacienteServiceImplTest {

    @Autowired
    private IPacienteService pacienteService;

    PacienteDTO dummyPac;
    PacienteDTO dummyPac2;
    PacienteDTO dummyPac3;
    DomicilioDTO domicilioGenerico;

    @BeforeEach
    public void cargarDatos() throws ServiceException, ResourceNotFoundException {
        domicilioGenerico = new DomicilioDTO();
        domicilioGenerico.setCalle("Sin Fin");
        domicilioGenerico.setNumero("S/N");
        domicilioGenerico.setLocalidad("Salsipuedes");
        domicilioGenerico.setProvincia("Neder");

        dummyPac = new PacienteDTO();
        dummyPac.setApellido("Tester");
        dummyPac.setNombre("Cosme");
        dummyPac.setDni("10.002.369");
        dummyPac.setDomicilio(domicilioGenerico);

        dummyPac2 = new PacienteDTO();
        dummyPac2.setApellido("Clavados");
        dummyPac2.setNombre("Fulanito");
        dummyPac2.setDni("05.468.247");
        dummyPac2.setDomicilio(domicilioGenerico);
        dummyPac2.setId(pacienteService.create(dummyPac2).getId());

    }

    @Test
    public void test01DebeCrearElPaciente() throws ServiceException, ResourceNotFoundException {
        //DADO>@BeforeEach

        //CUANDO
        PacienteDTO creado = pacienteService.create(dummyPac);

        //ENTONCES
        assertEquals(creado, dummyPac);
    }

    @Test
    public void test02NoDebeCrearElPaciente() throws ServiceException {
        //DADO
        PacienteDTO noPac = null;

        //ENTONCES > CUANDO
        assertThrows(ServiceException.class, () -> pacienteService.create(noPac));
    }


    @Test
    public void test03DebeTraerElPacienteBuscado() throws ServiceException, ResourceNotFoundException {
        //DADO>@BeforeEach

        //CUANDO
        PacienteDTO respuesta = pacienteService.readById(dummyPac2.getId()).get();

        //ENTONCES
        assertEquals(respuesta, dummyPac2);
    }

    @Test
    public void test04DebeActualizarElPaciente() throws ServiceException, ResourceNotFoundException {
        //DADO
        dummyPac2.setNombre("Misses");

        //CUANDO
        pacienteService.update(dummyPac2);
        Object respuesta = pacienteService.readById(dummyPac2.getId());

        //ENTONCES
        assertEquals(dummyPac2.getNombre(), "Misses");
    }

    @Test
    public void test05NoDebeActualizarElPacienteSinId() throws ServiceException, ResourceNotFoundException {
        //DADO
        dummyPac.setNombre("Tester");

        //ENTONCES > CUANDO
        assertThrows(ServiceException.class, () -> pacienteService.update(dummyPac));
    }

    @Test
    public void test06DebeEliminarPacientePorId() throws ResourceNotFoundException, ServiceException {
        //DADO
        dummyPac3 = new PacienteDTO();
        dummyPac3.setApellido("Tester");
        dummyPac3.setNombre("Quality");
        dummyPac3.setDni("36.547.001");
        dummyPac3.setDomicilio(domicilioGenerico);
        dummyPac3.setId(pacienteService.create(dummyPac3).getId());

        Long idPacienteParaBorrar = dummyPac3.getId();

        //CUANDO
        pacienteService.delete(idPacienteParaBorrar);

        //ENTONCES
        assertThrows(ResourceNotFoundException.class, () -> pacienteService.readById(idPacienteParaBorrar));
    }

    @Test
    public void test07DebeTraerTodosLosPacientes() {
        //DADO>@BeforeEach

        //CUANDO
        Collection<PacienteDTO> pacientes = pacienteService.getAll();

        //ENTONCES
        //assertFalse(odontologos.isEmpty());
        assertEquals(1, pacientes.size());
    }
}