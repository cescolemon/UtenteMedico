package com.nanosoft.ex.business.interfaces;

import java.util.List;


import com.nanosoft.ex.model.Utente;

public interface UtenteBO {
	
	Utente save(Utente utente) throws Exception;
    
    Utente findById(long id);
    
    List<Utente> findAll();
    
    void delete(Utente utente);
    
    Utente findByEmail(String email);
    

}
