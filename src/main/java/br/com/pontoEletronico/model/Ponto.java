package br.com.pontoEletronico.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import br.com.pontoEletronico.enums.TipoBatida;

@Entity
public class Ponto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Usuario usuario;
	
	@NotNull
	private LocalDateTime dataHoraBatida;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoBatida tipoBatida;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getDataHoraBatida() {
		return dataHoraBatida;
	}

	public void setDataHoraBatida(LocalDateTime dataHoraBatida) {
		this.dataHoraBatida = dataHoraBatida;
	}

	public TipoBatida getTipoBatida() {
		return tipoBatida;
	}

	public void setTipoBatida(TipoBatida tipoBatida) {
		this.tipoBatida = tipoBatida;
	}

}
