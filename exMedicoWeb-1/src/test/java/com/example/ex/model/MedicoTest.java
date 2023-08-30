package com.example.ex.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class MedicoTest {
    private Medico medico;

    @BeforeEach
    public void setUp() {
        medico = new Medico();
    }

    @Test
    public void testSetNome() {
        String name = "John";
        medico.setNome(name);

        Assertions.assertEquals(name, medico.getNome());
    }
    
    @Test
    public void testSetEmail() {
        String email = "medico_john@example.com";
        medico.setEmail(email);

        Assertions.assertEquals(email, medico.getEmail());
    }
    
    @Test
    public void testSetSpecializzazione() {
        String spec = "Chirurgo";
        medico.setSpecializzazione(spec);

        Assertions.assertEquals(spec, medico.getSpecializzazione());
    }
    
    @Test
    public void testSetAppuntamenti() {
		Utente utente = new Utente();
		utente.setNome("Doe");
		utente.setEmail("utente_doe@example.com");
		utente.setPassword("password1.");
		Appuntamento appuntamento= new Appuntamento();
		String data="07/22/2023";
		appuntamento.setData(LocalDate.parse(data, DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		appuntamento.setMedico(medico);
		appuntamento.setUtente(utente);
		List<Appuntamento> appuntamenti= new ArrayList<>();
		appuntamenti.add(appuntamento);
		medico.setAppuntamenti(appuntamenti);
		Assertions.assertEquals(appuntamenti, medico.getAppuntamenti());
	}
}
