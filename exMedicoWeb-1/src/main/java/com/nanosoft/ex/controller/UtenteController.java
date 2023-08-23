package com.nanosoft.ex.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nanosoft.ex.business.interfaces.AppuntamentoBO;
import com.nanosoft.ex.business.interfaces.MedicoBO;
import com.nanosoft.ex.business.interfaces.UtenteBO;
import com.nanosoft.ex.model.Appuntamento;
import com.nanosoft.ex.model.Medico;
import com.nanosoft.ex.model.Utente;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/user")
public class UtenteController {

    Logger logger = LoggerFactory.getLogger(UtenteController.class);


	@Autowired
	private UtenteBO utenteBO;
	@Autowired
	private AppuntamentoBO appuntamentoBO;
	@Autowired
	private MedicoBO medicoBO;
	
	
	@PostMapping("/register")
	@ResponseBody
    public ResponseEntity<?> registerUtente(@RequestBody Utente utente) {
        try {
        	String email= "utente_"+utente.getEmail();
        	utente.setEmail(email);
            Utente created = utenteBO.save(utente);
            logger.info("Registrazione utente: "+created.getNome()+" con email: "+created.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).header("Content-Type", "text/plain")
                    .body("Utente creato correttamente!");
        } catch (Exception e) {
        	logger.error("Eccezione: ",e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email già utilizzata!");
        }
    }
	
    @GetMapping("/appuntamenti")
    public ResponseEntity<?> getAllAppuntamentiUtente(@RequestParam("id_utente") String id ){
        List<Appuntamento> appuntamenti = new ArrayList<>();
        Utente curr = utenteBO.findById(Long.parseLong(id));
        appuntamenti = appuntamentoBO.findByUtente(curr);
        if(appuntamenti.size()==0) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nessun appuntamento trovato");
        }
        logger.info("Lista appuntamenti: "+appuntamenti.toString());
        return new ResponseEntity<List<Appuntamento>>(appuntamenti, HttpStatus.OK);
    }
    
    @PostMapping("/prenotazione")
    public ResponseEntity<?> registerAppuntamento(@RequestParam("id_utente") String id_utente, @RequestParam("id_medico")  String id_medico, @RequestParam("data")  String data) throws Exception{
    	try {   
    		
            LocalDate dataApp = LocalDate.parse(data, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            Medico medico= medicoBO.findById(Long.parseLong(id_medico));
            if(medico==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID medico errato!");
            Utente utente = utenteBO.findById(Long.parseLong(id_utente));
            if(medicoBO.limiteAppuntamenti(medico, dataApp)) { 
            	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Il medico scelto nella data specificata non è disponibile!");
            	}
            logger.info("Prenotazione appuntamento con il medico: "+medico.getNome()+" in data: "+data);
            Appuntamento saved =appuntamentoBO.save(utente,medico,dataApp);
            
            return new ResponseEntity<>(saved,HttpStatus.OK);
    	}catch (RuntimeException e) {
    		logger.error("Appuntamento non disponibile");
            Map<String,String> risp = new HashMap<>();
            risp.put("message",e.getMessage());
            return new ResponseEntity<>(risp,HttpStatus.BAD_REQUEST);

        }
    	
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppuntamento(@PathVariable Long id){
    	Appuntamento deleted= appuntamentoBO.findById(id);
    	 if(deleted==null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID appuntamento errato!");
        appuntamentoBO.delete(deleted);
        logger.info("Appuntamento eliminato: "+id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/medici")
    public ResponseEntity<?> getAllMedici(){
    List<Medico> medici = medicoBO.findAll();
    if(medici.size()==0) {
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nessun Medico trovato!");
    }
    logger.info("Lista Medici: "+medici.toString());
    return new ResponseEntity<List<Medico>>(medici, HttpStatus.OK);
}

	public void setMedicoBO(MedicoBO medicoBO) {
		this.medicoBO = medicoBO;
	}

}
