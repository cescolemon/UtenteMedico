package com.example.ex.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.Appuntamento;
import com.example.ex.model.Medico;
import com.example.ex.model.Utente;

@Repository
public interface AppuntamentoRepository extends JpaRepository<Appuntamento, Long> {
	
		Appuntamento save(Appuntamento appuntamento);
	    
		Appuntamento findById(long id);
	    
	    List<Appuntamento> findAll();
	    
	    void delete(Appuntamento appuntamento);
	    
	    List<Appuntamento> findByMedicoAndData(Medico medico, LocalDate data);
	    
	    List<Appuntamento> findByMedico(Medico medico);
	    
	    List<Appuntamento> findByUtente(Utente utente);
	    
	    int countByMedicoAndData(Medico medico, LocalDate data);

}
