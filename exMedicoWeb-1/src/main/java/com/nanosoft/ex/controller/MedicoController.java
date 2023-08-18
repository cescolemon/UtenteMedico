package com.nanosoft.ex.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nanosoft.ex.business.interfaces.AppuntamentoBO;
import com.nanosoft.ex.business.interfaces.MedicoBO;
import com.nanosoft.ex.model.Appuntamento;
import com.nanosoft.ex.model.Medico;


@RestController
@RequestMapping("/doctor")
public class MedicoController {
	
	Logger logger = LoggerFactory.getLogger(MedicoController.class);
	
	@Autowired
	private MedicoBO medicoBO;
	@Autowired
	private AppuntamentoBO appuntamentoBO;
	
	
	@PostMapping("/register")
    public ResponseEntity<?> registerMedico(@RequestBody Medico medico) {
        try {
        	String email="medico_"+medico.getEmail();
        	medico.setEmail(email);
            Medico created = medicoBO.save(medico);
            logger.info("Registrazione medico: "+created.getNome()+" con email: "+created.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("Medico creato correttamente!");
        } catch (Exception e) {
        	logger.error("Eccezione: ",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email già utilizzata!");
        }
    }
	
	 @GetMapping("/appuntamenti")
	// @PreAuthorize("hasRole('MEDICO')")
	    public ResponseEntity<?> getAllAppuntamentiMedico(@RequestParam("id_medico") String id ){

		 	List<Appuntamento> appuntamenti = new ArrayList<>();
	        Medico curr = medicoBO.findById(Long.parseLong(id));
	        appuntamenti = appuntamentoBO.findByMedico(curr);
	        if(appuntamenti.size()==0) {
	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nessun appuntamento trovato");
	        }
	        logger.info("Lista appuntamenti: "+appuntamenti.toString());
	        return new ResponseEntity<List<Appuntamento>>(appuntamenti, HttpStatus.OK);
	 	}
}
