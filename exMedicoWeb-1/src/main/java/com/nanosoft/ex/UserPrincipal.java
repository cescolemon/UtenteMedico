package com.nanosoft.ex;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nanosoft.ex.model.Appuntamento;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Long id;
    private String email;
    private String password;
    private String nome;
    private List<Appuntamento> appuntamenti;
    private String specializzazione;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id,String nome, String email, String password, List<Appuntamento> appuntamenti, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nome=nome;
        this.email = email;
        this.password = password;
        this.appuntamenti=appuntamenti;
        this.authorities = authorities;
    }
    
    public UserPrincipal(Long id,String nome, String email, String password,String specializzazione, List<Appuntamento> appuntamenti, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nome=nome;
        this.email = email;
        this.password = password;
        this.specializzazione=specializzazione;
        this.appuntamenti=appuntamenti;
        this.authorities = authorities;
    }

    public List<Appuntamento> getAppuntamenti() {
		return appuntamenti;
	}

	public void setAppuntamenti(List<Appuntamento> appuntamenti) {
		this.appuntamenti = appuntamenti;
	}

	public String getSpecializzazione() {
		return specializzazione;
	}

	public void setSpecializzazione(String specializzazione) {
		this.specializzazione = specializzazione;
	}

	public String getEmail() {
        return email;
    }
    
    public Long getId() {
        return id;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}