package com.example.ex.business.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.example.ex.model.Medico;


public interface MedicoBO {

	Medico save(Medico medico) throws Exception;
    
    Medico findById(long id);
    
    List<Medico> findAll();
    
    void delete(Medico medico);
    
    Medico findByEmail(String email);
    
    
    boolean limiteAppuntamenti(Medico medico, LocalDate data);
}
