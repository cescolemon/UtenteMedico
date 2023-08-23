package com.nanosoft.ex.business.impl;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nanosoft.ex.UserPrincipal;
import com.nanosoft.ex.business.interfaces.MedicoBO;
import com.nanosoft.ex.business.interfaces.UtenteBO;
import com.nanosoft.ex.model.Medico;
import com.nanosoft.ex.model.Utente;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {
	
	@Autowired
	private UtenteBO utenteBO;
	@Autowired
	private MedicoBO medicoBO;

	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Utente utente= new Utente();
		Medico medico =new Medico();
		if(email.startsWith("utente")) {
        utente = utenteBO.findByEmail(email);
        if (utente == null) {
            throw new UsernameNotFoundException("Utente non trovato: " + email);
        }
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
       UserPrincipal userPrincipal=new UserPrincipal(
               utente.getId(),
               utente.getNome(),
               utente.getEmail(),
               utente.getPassword(),
               utente.getAppuntamenti(),
               authorities
           );
        return userPrincipal;
		}else if (email.startsWith("medico")) {
			medico = medicoBO.findByEmail(email);
			 if (medico == null) {
		            throw new UsernameNotFoundException("Utente non trovato: " + email);
		        }
			 List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_MEDICO"));
		     return new UserPrincipal(
		                medico.getId(),
		                medico.getNome(),
		                medico.getEmail(),
		                medico.getPassword(),
		                medico.getSpecializzazione(),
		                medico.getAppuntamenti(),
		                authorities
		            );
		}


		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        return new UserPrincipal(
            utente.getId(),
            utente.getNome(),
            utente.getEmail(),
            utente.getPassword(),
            utente.getAppuntamenti(),
            authorities
        );
    }
	

}
