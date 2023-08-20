package com.nanosoft.ex.business.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nanosoft.ex.business.interfaces.MedicoBO;
import com.nanosoft.ex.model.Medico;
import com.nanosoft.ex.model.Utente;
import com.nanosoft.ex.repository.AppuntamentoRepository;
import com.nanosoft.ex.repository.MedicoRepository;
import com.nanosoft.ex.repository.UtenteRepository;

@Service
@Transactional
public class MedicoBOImpl implements MedicoBO{
	
	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private AppuntamentoRepository appuntamentoRepository;
	
	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	public MedicoBOImpl(MedicoRepository medicoRepository, PasswordEncoder passwordEncoder) {
        this.medicoRepository = medicoRepository;
       this.passwordEncoder = passwordEncoder;
    }

	@Override
	public Medico save(Medico medico) throws Exception {
		if (medicoRepository.findByEmail(medico.getEmail()) != null) {
            throw new Exception("Email già utilizzata!");
        }
		
		medico.setPassword(passwordEncoder.encode(medico.getPassword()));
		return medicoRepository.save(medico);
	}

	@Override
	public Medico findById(long id) {

		return medicoRepository.findById(id);
	}

	@Override
	public List<Medico> findAll() {

		return medicoRepository.findAll();
	}

	@Override
	public void delete(Medico medico) {
		medicoRepository.delete(medico);
		
	}

	@Override
	public Medico findByEmail(String email) {
		
		return medicoRepository.findByEmail(email);
	}


	@Override
	public boolean limiteAppuntamenti(Medico medico, LocalDate data) {
		 int count = appuntamentoRepository.countByMedicoAndData(medico, data);
	     return count >=10;
	}

}
