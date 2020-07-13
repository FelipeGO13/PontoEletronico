package br.com.pontoEletronico.dto;

import javax.validation.constraints.NotNull;

public class PontoDTO {

	@NotNull
	private String dataHoraBatida;

	@NotNull
	private String tipoBatida;

	public String getDataHoraBatida() {
		return dataHoraBatida;
	}

	public void setDataHoraBatida(String dataHoraBatida) {
		this.dataHoraBatida = dataHoraBatida;
	}

	public String getTipoBatida() {
		return tipoBatida;
	}

	public void setTipoBatida(String tipoBatida) {
		this.tipoBatida = tipoBatida;
	}

}
