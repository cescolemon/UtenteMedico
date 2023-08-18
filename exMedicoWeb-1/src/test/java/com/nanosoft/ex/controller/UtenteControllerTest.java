package com.nanosoft.ex.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.nanosoft.ex.business.interfaces.AppuntamentoBO;
import com.nanosoft.ex.business.interfaces.MedicoBO;
import com.nanosoft.ex.business.interfaces.UtenteBO;
import com.nanosoft.ex.model.Appuntamento;
import com.nanosoft.ex.model.Medico;
import com.nanosoft.ex.model.Utente;
import com.nanosoft.ex.repository.AppuntamentoRepository;
import com.nanosoft.ex.repository.UtenteRepository;

@SpringBootTest
public class UtenteControllerTest {
	
	private MockMvc mockMvc;

    @Mock
    private UtenteRepository utenteRepository;

    @Mock
    private AppuntamentoRepository appuntamentoRepository;

    @Mock
    private AppuntamentoBO appuntamentoBO;

    @Mock
    private MedicoBO medicoBO;

    @Mock
    private UtenteBO utenteBO;


    @InjectMocks
    private UtenteController utenteController;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(utenteController).build();
    }

    @Test
    public void testRegisterUtente() throws Exception {
        Utente utente = new Utente();
        utente.setEmail("utente_test@nanosoft.com");

        Mockito.when(utenteBO.save(any(Utente.class))).thenReturn(utente);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"utente_test@nanosoft.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Utente creato correttamente!"));
    }
    
    @Test
    public void testRegisterUtente_EmailEsistente() throws Exception {
        Utente utente = new Utente();
        utente.setEmail("utente_test@nanosoft.com");

        doThrow(new RuntimeException("Email già utilizzata!")).when(utenteBO).save(any(Utente.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"utente_test@nanosoft.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Email già utilizzata!"));
    }

        @Test
        public void testRegisterAppuntamento_MedicoDisponibile() throws Exception {

            String idUtente = "1";
            String idMedico = "2";
            String data = "01/01/2022";
            LocalDate dataApp = LocalDate.parse(data, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            Medico medico = new Medico();
            Utente utente = new Utente();
            Appuntamento savedAppuntamento = new Appuntamento();

            when(medicoBO.findById(Long.parseLong(idMedico))).thenReturn(medico);
            when(utenteBO.findById(Long.parseLong(idUtente))).thenReturn(utente);
            when(medicoBO.limiteAppuntamenti(medico, dataApp)).thenReturn(false);  
            when(appuntamentoBO.save(utente, medico, dataApp)).thenReturn(savedAppuntamento);


            mockMvc.perform(post("/user/prenotazione")
                    .param("id_utente", idUtente)
                    .param("id_medico", idMedico)
                    .param("data", data))
                    .andExpect(status().isOk());

        }

        @Test
        public void testRegisterAppuntamento_MedicoNonDisponibile() throws Exception {

            String idUtente = "1";
            String idMedico = "2";
            String data = "01/01/2022";
            LocalDate dataApp = LocalDate.parse(data, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            Medico medico = new Medico();
            Utente utente = new Utente();

            when(medicoBO.findById(Long.parseLong(idMedico))).thenReturn(medico);
            when(utenteBO.findById(Long.parseLong(idUtente))).thenReturn(utente);
            when(medicoBO.limiteAppuntamenti(medico, dataApp)).thenReturn(true);

            mockMvc.perform(post("/user/prenotazione")
                    .param("id_utente", idUtente)
                    .param("id_medico", idMedico)
                    .param("data", data))
                    .andExpect(status().isBadRequest());

        }

        @Test
        public void testRegisterAppuntamento_RuntimeException() throws Exception {

            String idUtente = "1";
            String idMedico = "2";
            String data = "01/01/2022";
            LocalDate dataApp = LocalDate.parse(data, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            Medico medico = new Medico();
            Utente utente = new Utente();
            String errorMessage = "Appuntamento non disponibile";

            when(medicoBO.findById(Long.parseLong(idMedico))).thenReturn(medico);
            when(utenteBO.findById(Long.parseLong(idUtente))).thenReturn(utente);
            when(medicoBO.limiteAppuntamenti(medico, dataApp)).thenReturn(false);
            when(appuntamentoBO.save(utente, medico, dataApp)).thenThrow(new RuntimeException(errorMessage));

            mockMvc.perform(post("/user/prenotazione")
                    .param("id_utente", idUtente)
                    .param("id_medico", idMedico)
                    .param("data", data))
                    .andExpect(status().isBadRequest());
        }

    
        @Test
        void testGetAllAppuntamentiUtente() {
            String id = "1";
            List<Appuntamento> appuntamenti = new ArrayList<>();
            Utente curr = new Utente();
            curr.setId(Long.parseLong(id));
            appuntamenti.add(new Appuntamento());
            when(utenteBO.findById(Long.parseLong(id))).thenReturn(curr);
            when(appuntamentoBO.findByUtente(curr)).thenReturn(appuntamenti);

            ResponseEntity<?> response = utenteController.getAllAppuntamentiUtente(id);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(appuntamenti, response.getBody());
        }
    
    @Test
    public void testDeleteAppuntamento() throws Exception {    	
    	mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", 1L))
        .andExpect(status().isNoContent());
    }
}