package com.example.ex.business.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.example.ex.model.Appuntamento;
import com.example.ex.model.Medico;
import com.example.ex.model.Utente;

public interface AppuntamentoBO {
	
	Appuntamento save(Utente utente, Medico medico, LocalDate data) throws Exception;
    
	Appuntamento findById(long id);
    
    List<Appuntamento> findAll();
    
    void delete(Appuntamento appuntamento);
    
    List<Appuntamento> findByMedicoAndData(Medico medico, LocalDate data);
    
    List<Appuntamento> findByMedico(Medico medico);
    
    List<Appuntamento> findByUtente(Utente utente);

}
