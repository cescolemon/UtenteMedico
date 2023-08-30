package com.example.ex.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class AppuntamentoTest {
	
	private Appuntamento appuntamento;
	
    @BeforeEach
    public void setUp() {
        appuntamento = new Appuntamento();
    }

    @Test
    public void testSetData() {
        Appuntamento appuntamento = new Appuntamento();
 
        LocalDate data = LocalDate.of(2023, 7, 10);

        appuntamento.setData(data);

        assertEquals(data, appuntamento.getData());
    }

    @Test
    public void testSetMedico() {
    	Medico medico = new Medico();
		medico.setNome("Doe");
		medico.setEmail("medico_doe@example.com");
		medico.setSpecializzazione("Chirurgo");
		appuntamento.setMedico(medico);
		Assertions.assertEquals(medico, appuntamento.getMedico());
    }
    
    @Test
    public void testSetUtente() {
    	Utente utente = new Utente();
		utente.setNome("Doe");
		utente.setEmail("utente_doe@example.com");
		utente.setPassword("password1.");
		appuntamento.setUtente(utente);
		Assertions.assertEquals(utente, appuntamento.getUtente());
    }

}
