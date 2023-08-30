package com.example.ex.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import com.example.ex.business.interfaces.AppuntamentoBO;
import com.example.ex.business.interfaces.MedicoBO;
import com.example.ex.model.Appuntamento;
import com.example.ex.model.Medico;
import com.example.ex.model.Utente;
import com.example.ex.repository.AppuntamentoRepository;
import com.example.ex.repository.MedicoRepository;


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
        medico.setEmail("medico_test@example.com");

        Mockito.when(medicoBO.save(any(Medico.class))).thenReturn(medico);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"medico_test@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Medico creato correttamente!"));
    }
    
    @Test
    public void testRegisterMedico_EmailEsistente() throws Exception {
    	Medico medico = new Medico();
        medico.setEmail("medico_test@example.com");

        when(medicoBO.findByEmail(anyString())).thenReturn(medico);

        mockMvc.perform(MockMvcRequestBuilders.post("/doctor/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"medico_test@example.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Email già utilizzata!"));
    }

    @Test
    public void testGetAllAppuntamentiMedico() throws Exception {
        String id = "1";

        Medico medico = new Medico();
        
        when(medicoBO.findById(Long.parseLong(id))).thenReturn(medico);
 
        List<Appuntamento> appuntamenti = new ArrayList<>();
        String data = "01/01/2022";
        LocalDate dataApp = LocalDate.parse(data, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        appuntamenti.add(appuntamentoBO.save(new Utente(), medico, dataApp));
        when(appuntamentoBO.findByMedico(medico)).thenReturn(appuntamenti);
 
        ResponseEntity<?> responseEntity = medicoController.getAllAppuntamentiMedico(id);
 
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(appuntamenti, responseEntity.getBody());
    }
 
    @Test
    public void testGetAllAppuntamentiMedicoNoAppuntamenti() {
        String id = "1";

        Medico medico = new Medico();
        when(medicoBO.findById(Long.parseLong(id))).thenReturn(medico);
 
        List<Appuntamento> appuntamenti = new ArrayList<>();
        when(appuntamentoBO.findByMedico(medico)).thenReturn(appuntamenti);

        ResponseEntity<?> responseEntity = medicoController.getAllAppuntamentiMedico(id);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Nessun appuntamento trovato", responseEntity.getBody());
    }
    

}
