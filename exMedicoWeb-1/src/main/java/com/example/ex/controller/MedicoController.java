package com.example.ex.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex.business.interfaces.AppuntamentoBO;
import com.example.ex.business.interfaces.MedicoBO;
import com.example.ex.model.Appuntamento;
import com.example.ex.model.Medico;



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
        	Medico emailUsed= medicoBO.findByEmail(email);
        	if(emailUsed!= null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email già utilizzata!");
        	medico.setEmail(email);
            Medico created = medicoBO.save(medico);
            logger.info("Registrazione medico: "+created.getNome()+" con email: "+created.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body("Medico creato correttamente!");
        } catch (Exception e) {
        	logger.error("Eccezione: ",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
        }
    }
	
	 @GetMapping("/appuntamenti")
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
