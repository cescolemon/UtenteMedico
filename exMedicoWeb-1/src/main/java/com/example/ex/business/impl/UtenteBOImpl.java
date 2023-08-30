package com.example.ex.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ex.business.interfaces.UtenteBO;
import com.example.ex.model.Appuntamento;
import com.example.ex.model.Utente;
import com.example.ex.repository.UtenteRepository;

@Service
public class UtenteBOImpl implements UtenteBO{
	
	@Autowired
	private UtenteRepository utenteRepository;
	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	
	public UtenteBOImpl(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder) {
        this.utenteRepository = utenteRepository;
       this.passwordEncoder = passwordEncoder;
    }
	


	@Override
	public Utente save(Utente utente) throws Exception {
        if (utenteRepository.findByEmail(utente.getEmail()) != null) {
            throw new Exception("Email già utilizzata!");
        }
       utente.setPassword(passwordEncoder.encode(utente.getPassword()));

        return utenteRepository.save(utente);
	}

	@Override
	public Utente findById(long id) {

		return utenteRepository.findById(id);
	}

	@Override
	public List<Utente> findAll() {

		return utenteRepository.findAll();
	}

	@Override
	public void delete(Utente utente) {
		utenteRepository.delete(utente);
	}

	@Override
	public Utente findByEmail(String email) {

		return utenteRepository.findByEmail(email);
	}

}
