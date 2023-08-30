package com.example.ex.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Appuntamento extends BaseEntity{
	
    @Column(columnDefinition = "DATE")
    private LocalDate data;
    
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private Medico medico;
    
    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, medico, utente);
	}

	@Override
	public String toString() {
		return "Appuntamento [data=" + data.toString() + ", medico=" + medico.getNome() + ", utente=" + utente.getNome() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appuntamento other = (Appuntamento) obj;
		return Objects.equals(data, other.data) && Objects.equals(medico, other.medico)
				&& Objects.equals(utente, other.utente);
	}


}
