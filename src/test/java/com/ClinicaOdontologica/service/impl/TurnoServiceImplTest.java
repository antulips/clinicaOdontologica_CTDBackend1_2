package com.ClinicaOdontologica.service.impl;

import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.model.dto.DomicilioDTO;
import com.ClinicaOdontologica.model.dto.OdontologoDTO;
import com.ClinicaOdontologica.model.dto.PacienteDTO;
import com.ClinicaOdontologica.model.dto.TurnoDTO;
import com.ClinicaOdontologica.service.IOdontologoService;
import com.ClinicaOdontologica.service.IPacienteService;
import com.ClinicaOdontologica.service.ITurnoService;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class TurnoServiceImplTest {

    @Autowired
    private IPacienteService pacienteService;

    @Autowired
    private IOdontologoService odontologoService;

    @Autowired
    private ITurnoService turnoService;

    TurnoDTO dummyTurno;
    TurnoDTO dummyTurno2;
    TurnoDTO dummyTurno3;
    PacienteDTO dummyPac;
    DomicilioDTO dummyDomicilio;
    OdontologoDTO dummyOd;

    @BeforeEach
    public void cargarDatos() throws ServiceException, ResourceNotFoundException {
        dummyDomicilio = new DomicilioDTO();
        dummyDomicilio.setCalle("Sin Fin");
        dummyDomicilio.setNumero("S/N");
        dummyDomicilio.setLocalidad("Salsipuedes");
        dummyDomicilio.setProvincia("Neder");

        dummyPac = new PacienteDTO();
        dummyPac.setApellido("Tester");
        dummyPac.setNombre("Cosme");
        dummyPac.setDni("10.002.369");
        dummyPac.setDomicilio(dummyDomicilio);
        dummyPac.setId(pacienteService.create(dummyPac).getId());

        dummyOd = new OdontologoDTO();
        dummyOd.setApellido("Clavados");
        dummyOd.setNombre("Fulanito");
        dummyOd.setMatricula("14587/G-8");
        dummyOd.setId(odontologoService.create(dummyOd).getId());

        dummyTurno = new TurnoDTO();
        dummyTurno.setHora(LocalTime.of(10, 45));
        dummyTurno.setFecha(LocalDate.of(2021, 10, 20));
        dummyTurno.setOdontologo(dummyOd);
        dummyTurno.setPaciente(dummyPac);

        dummyTurno2 = new TurnoDTO();
        dummyTurno2.setHora(LocalTime.of(11, 00));
        dummyTurno2.setFecha(LocalDate.of(2021, 10, 10));
        dummyTurno2.setOdontologo(dummyOd);
        dummyTurno2.setPaciente(dummyPac);
        dummyTurno2.setId(turnoService.create(dummyTurno2).getId());

        dummyTurno3 = new TurnoDTO();
        dummyTurno3.setHora(LocalTime.of(16, 45));
        dummyTurno3.setFecha(LocalDate.of(2021, 10, 15));
        dummyTurno3.setOdontologo(dummyOd);
        dummyTurno3.setPaciente(dummyPac);
        dummyTurno3.setId(turnoService.create(dummyTurno3).getId());
    }

    @Test
    public void test01DebeCrearElTurno() throws ServiceException, ResourceNotFoundException {
        //DADO>@BeforeEach

        //CUANDO
        TurnoDTO creado = turnoService.create(dummyTurno);

        //ENTONCES
        assertEquals(creado, dummyTurno);
    }

    @Test
    public void test02NoDebeCrearElTurno() throws ServiceException {
        //DADO
        TurnoDTO noTur = null;

        //ENTONCES > CUANDO
        assertThrows(ServiceException.class, () -> turnoService.create(noTur));
    }


    @Test
    public void test03DebeTraerElTurnoBuscado() throws ServiceException, ResourceNotFoundException {
        //DADO>@BeforeEach

        //CUANDO
        TurnoDTO respuesta = turnoService.readById(dummyTurno2.getId()).get();

        //ENTONCES
        assertEquals(respuesta, dummyTurno2);
    }

    @Test
    public void test04DebeActualizarElTurno() throws ServiceException, ResourceNotFoundException {
        //DADO
        dummyTurno2.setHora(LocalTime.of(10, 30));

        //CUANDO
        turnoService.update(dummyTurno2);
        Object respuesta = turnoService.readById(dummyTurno2.getId());

        //ENTONCES
        assertEquals(dummyTurno2.getHora(), LocalTime.of(10,30));
    }

    @Test
    public void test05NoDebeActualizarElTurnoSinId() throws ServiceException, ResourceNotFoundException {
        //DADO
        dummyTurno.setHora(LocalTime.of(10, 30));

        //ENTONCES > CUANDO
        assertThrows(ServiceException.class, () -> turnoService.update(dummyTurno));
    }

    @Test
    public void test06DebeEliminarTurnoPorId() throws ResourceNotFoundException, ServiceException {
        //DADO>@BeforeEach

        Long idTurnoParaBorrar = dummyTurno3.getId();

        //CUANDO
        turnoService.delete(idTurnoParaBorrar);

        //ENTONCES
        assertThrows(ResourceNotFoundException.class, () -> turnoService.readById(idTurnoParaBorrar));
    }

    @Test
    public void test07DebeTraerTodosLosTurnos() {
        //DADO>@BeforeAll

        //CUANDO
        Collection<TurnoDTO> turnos = turnoService.getAll();

        //ENTONCES
        //assertFalse(odontologos.isEmpty());
        assertEquals(10, turnos.size());
    }
    //todo: agregar nuevos métodos de rangos y semana.
}