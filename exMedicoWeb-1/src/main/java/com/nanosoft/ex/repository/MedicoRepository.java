package com.nanosoft.ex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nanosoft.ex.model.Medico;


@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
	
	 	Medico save(Medico medico);
	    
	    Medico findById(long id);
	    
	    List<Medico> findAll();
	    
	    void delete(Medico medico);
	    
	    Medico findByEmail(String email);
	    
	    

}
