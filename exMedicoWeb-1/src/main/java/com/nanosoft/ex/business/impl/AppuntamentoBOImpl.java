package com.nanosoft.ex.business.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nanosoft.ex.business.interfaces.AppuntamentoBO;
import com.nanosoft.ex.model.Appuntamento;
import com.nanosoft.ex.model.Medico;
import com.nanosoft.ex.model.Utente;
import com.nanosoft.ex.repository.AppuntamentoRepository;

@Service
public class AppuntamentoBOImpl implements AppuntamentoBO {
	
	@Autowired
	private AppuntamentoRepository appuntamentoRepository;
	
	
	@Autowired
	public AppuntamentoBOImpl(AppuntamentoRepository appuntamentoRepository) {
	    this.appuntamentoRepository = appuntamentoRepository;
	}

	@Override
	public Appuntamento save(Utente utente, Medico medico, LocalDate data) throws Exception {
		Appuntamento appuntamento = new Appuntamento();
        appuntamento.setUtente(utente);
        appuntamento.setMedico(medico);
        appuntamento.setData(data);
		for(Appuntamento a : appuntamentoRepository.findAll()) {
			if(a.equals(appuntamento)) {
				throw new Exception("Appuntamento non disponibile");
			}
		}
		
        return appuntamentoRepository.save(appuntamento);
	}

	@Override
	public Appuntamento findById(long id) {

		return appuntamentoRepository.findById(id);
	}

	@Override
	public List<Appuntamento> findAll() {
		
		return appuntamentoRepository.findAll();
	}

	@Override
	public void delete(Appuntamento appuntamento) {
		appuntamentoRepository.delete(appuntamento);
		
	}

	@Override
	public List<Appuntamento> findByMedicoAndData(Medico medico, LocalDate data) {
		
		return appuntamentoRepository.findByMedicoAndData(medico, data);
	}

	@Override
	public List<Appuntamento> findByMedico(Medico medico) {
		
		return appuntamentoRepository.findByMedico(medico);
	}

	@Override
	public List<Appuntamento> findByUtente(Utente utente) {
		
		return appuntamentoRepository.findByUtente(utente);
	}

}
