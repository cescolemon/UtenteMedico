package com.example.ex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ex.model.Medico;


@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
	
	 	Medico save(Medico medico);
	    
	    Medico findById(long id);
	    
	    List<Medico> findAll();
	    
	    void delete(Medico medico);
	    
	    Medico findByEmail(String email);
	    
	    

}
