package com.example.ex.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Utente extends BaseEntity {
	
	private String nome;
    private String email;
    private String password;
    
    @JsonIgnore
    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL)
    private List<Appuntamento> appuntamenti;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Appuntamento> getAppuntamenti() {
		return appuntamenti;
	}

	public void setAppuntamenti(List<Appuntamento> appuntamenti) {
		this.appuntamenti = appuntamenti;
	}

}
