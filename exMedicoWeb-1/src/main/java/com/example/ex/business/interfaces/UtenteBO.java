package com.example.ex.business.interfaces;

import java.util.List;

import com.example.ex.model.Utente;

public interface UtenteBO {
	
	Utente save(Utente utente) throws Exception;
    
    Utente findById(long id);
    
    List<Utente> findAll();
    
    void delete(Utente utente);
    
    Utente findByEmail(String email);
    

}
