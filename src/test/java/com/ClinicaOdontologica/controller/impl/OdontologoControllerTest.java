package com.ClinicaOdontologica.controller.impl;

import com.ClinicaOdontologica.exceptions.GlobalExceptions;
import com.ClinicaOdontologica.exceptions.ResourceNotFoundException;
import com.ClinicaOdontologica.exceptions.ServiceException;
import com.ClinicaOdontologica.model.dto.OdontologoDTO;
import com.ClinicaOdontologica.service.IOdontologoService;
import com.ClinicaOdontologica.util.Jsons;
import com.ClinicaOdontologica.util.Messages;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@WebMvcTest(OdontologoController.class)
@AutoConfigureMockMvc(addFilters = false)
@RunWith(MockitoJUnitRunner.class)
public class OdontologoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IOdontologoService odontologoService;

    @InjectMocks
    private OdontologoController odontologoController;

    OdontologoDTO dummyOdToSend;
    OdontologoDTO dummyOdReceived;
    OdontologoDTO noO;

    @Before
    public void cargarDatos() throws ServiceException, ResourceNotFoundException {
        mockMvc = MockMvcBuilders.standaloneSetup(odontologoController).setControllerAdvice(GlobalExceptions.class).build();

        //ODONTÓLOGO A ENVIAR
        dummyOdToSend = new OdontologoDTO();
        dummyOdToSend.setApellido("Tester");
        dummyOdToSend.setNombre("Doctor o");
        dummyOdToSend.setMatricula("FKH 1245/6 ");

        //ODONTÓLOGO RESPUESTA
        dummyOdReceived = new OdontologoDTO();
        dummyOdReceived.setApellido("Tester");
        dummyOdReceived.setNombre("Doctor o");
        dummyOdReceived.setMatricula("FKH 1245/6 ");
        dummyOdReceived.setId(1L);

        //ODONTÓLOGO NULL
        noO = null;

    }

    @Test
    public void test01DebeCrearElOdontologo() throws Exception {
        //DADO>
        Mockito.when(odontologoService.create(dummyOdToSend)).thenReturn(dummyOdReceived);

        //ENTONCES > CUANDO
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/odontologos/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(Jsons.asJsonString(dummyOdToSend)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        assertEquals((String.format(Messages.CREADO_CON_EXITO, "odontólogo", "1")), response.getResponse().getContentAsString());
    }

    @Test
    public void test02NoDebeCrearElOdontologo() throws Exception {
        //DADO>@Before

        //ENTONCES > CUANDO
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/odontologos/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(Jsons.asJsonString(noO)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is5xxServerError()).andReturn();

        assertEquals(("Se produjo un error inesperado, vuelva intentar más tarde, por favor."), response.getResponse().getContentAsString());
    }

    @Test
    public void test03DebeTraerElOdontologoBuscado() throws Exception {
        //DADO>
        Mockito.when(odontologoService.readById(dummyOdReceived.getId())).thenReturn(Optional.of(dummyOdReceived));

        //ENTONCES > CUANDO
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/{id}", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(Jsons.asJsonString(dummyOdReceived), response.getResponse().getContentAsString());
    }

    @Test
    public void test04DebeActualizarElOdontologo() throws Exception {
        //DADO>
        Mockito.when(odontologoService.update(dummyOdReceived)).thenReturn(dummyOdReceived);

        //ENTONCES > CUANDO
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/odontologos/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(Jsons.asJsonString(dummyOdReceived)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        assertEquals(Jsons.asJsonString(dummyOdReceived), response.getResponse().getContentAsString());
    }

    @Test
    public void test05NoDebeActualizarElOdontologoSinId() throws Exception {
        //DADO>@Before
        Mockito.when(odontologoService.update(dummyOdToSend)).thenThrow(new ServiceException("Debe proporcionar un id para actualizar un odontólogo."));

        //ENTONCES > CUANDO
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put("/odontologos/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(Jsons.asJsonString(dummyOdToSend)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();

        assertEquals(("Error: \nDebe proporcionar un id para actualizar un odontólogo."), response.getResponse().getContentAsString());
    }

    @Test
    public void test06DebeEliminarOdontologoPorId() throws Exception {
        //DADO>
        Mockito.when(odontologoService.delete(dummyOdReceived.getId())).thenReturn("El siguiente odontologo fue eliminado con éxito:\n" + dummyOdReceived.toString());

        //ENTONCES > CUANDO
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete("/odontologos/delete/{id}", "1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertEquals(("El siguiente odontologo fue eliminado con éxito:\n" + dummyOdReceived.toString()), response.getResponse().getContentAsString());
    }

    @Test
    public void test07DebeTraerTodosLosOdontologos() throws Exception {
        //DADO>@BeforeAll

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get("/odontologos/all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}