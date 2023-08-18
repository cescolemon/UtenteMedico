package com.nanosoft.ex.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UtenteTest {
    private Utente utente;

    @BeforeEach
    public void setUp() {
        utente = new Utente();
    }

    @Test
    public void testSetNome() {
        String name = "John";
        utente.setNome(name);

        Assertions.assertEquals(name, utente.getNome());
    }

    @Test
    public void testSetEmail() {
        String email = "utente_john@nanosoft.com";
        utente.setEmail(email);

        Assertions.assertEquals(email, utente.getEmail());
    }
    
    @Test
	public void testSetPassword() {
		String password = "password1.";
        utente.setPassword(password);

        Assertions.assertEquals(password, utente.getPassword());
	}
	
    @Test
	public void testSetAppuntamenti() {
		Medico medico = new Medico();
		medico.setNome("Doe");
		medico.setEmail("medico_doe@nanosoft.com");
		medico.setSpecializzazione("Chirurgo");
		Appuntamento appuntamento= new Appuntamento();
		String data="07/22/23";
		appuntamento.setData(LocalDate.parse("07/22/2023", DateTimeFormatter.ofPattern("MM/dd/yyyy")));
		appuntamento.setMedico(medico);
		appuntamento.setUtente(utente);
		List<Appuntamento> appuntamenti= new ArrayList<>();
		appuntamenti.add(appuntamento);
		utente.setAppuntamenti(appuntamenti);
		Assertions.assertEquals(appuntamenti, utente.getAppuntamenti());
	}

}