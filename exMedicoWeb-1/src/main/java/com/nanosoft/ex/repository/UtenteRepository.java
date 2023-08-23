package com.nanosoft.ex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nanosoft.ex.model.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
	
	 	Utente save(Utente utente);
	    
	    Utente findById(long id);
	    
	    List<Utente> findAll();
	    
	    void delete(Utente utente);
	    
	    Utente findByEmail(String email);
	    
	
	
}
