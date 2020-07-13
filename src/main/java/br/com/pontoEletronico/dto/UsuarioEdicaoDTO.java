package br.com.pontoEletronico.dto;

import org.hibernate.validator.constraints.br.CPF;

public class UsuarioEdicaoDTO {

	private String nome;
	
	@CPF
	private String cpf;
	
	private String email;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



}
