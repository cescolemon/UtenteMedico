package com.nanosoft.ex.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nanosoft.ex.business.interfaces.AppuntamentoBO;
import com.nanosoft.ex.business.interfaces.MedicoBO;
import com.nanosoft.ex.model.Appuntamento;
import com.nanosoft.ex.model.Medico;
import com.nanosoft.ex.model.Utente;
import com.nanosoft.ex.repository.AppuntamentoRepository;
import com.nanosoft.ex.repository.MedicoRepository;


@SpringBootTest
public class MedicoControllerTest {

	private MockMvc mockMvc;

    @Mock
    private MedicoRepository medicoRepository;
    @Mock
    private AppuntamentoRepository appuntamentoRepository;
    @Mock
    private MedicoBO medicoBO;
    @Mock
    private AppuntamentoBO appuntamentoBO;

    @InjectMocks
    private MedicoController medicoController;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(medicoController).build();
    }
    
    @Test
    public void testRegisterMedico() throws Exception {
        Medico medico = new Medico();
        medico.setEmail("medico_test@nanosoft.com");

        Mockito.when(medicoBO.save(any(Medico.class))).thenReturn(medico);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"medico_test@nanosoft.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Medico creato correttamente!"));
    }
    
    @Test
    public void testRegisterMedico_EmailEsistente() throws Exception {
    	Medico medico = new Medico();
        medico.setEmail("medico_test@nanosoft.com");

        doThrow(new RuntimeException("Email già utilizzata!")).when(medicoBO).save(any(Medico.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"medico_test@nanosoft.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Email già utilizzata!"));
    }

    @Test
    void testGetAllAppuntamentiMedico() {
        String id = "1";
        Medico medico = new Medico();
        medico.setId(Long.parseLong(id));
        List<Appuntamento> appuntamenti = new ArrayList<>();
        appuntamenti.add(new Appuntamento());

        when(medicoBO.findById(Long.parseLong(id))).thenReturn(medico);
        when(appuntamentoBO.findByMedico(medico)).thenReturn(appuntamenti);

        ResponseEntity<?> response = medicoController.getAllAppuntamentiMedico(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appuntamenti, response.getBody());
    }

    @Test
    void testGetAllAppuntamentiMedico_NoAppuntamenti() {
        String id = "1";
        Medico medico = new Medico();
        medico.setId(Long.parseLong(id));
        List<Appuntamento> appuntamenti = new ArrayList<>();

        when(medicoBO.findById(Long.parseLong(id))).thenReturn(medico);
        when(appuntamentoBO.findByMedico(medico)).thenReturn(appuntamenti);

        ResponseEntity<?> response = medicoController.getAllAppuntamentiMedico(id);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Nessun appuntamento trovato", response.getBody());
    }
}
