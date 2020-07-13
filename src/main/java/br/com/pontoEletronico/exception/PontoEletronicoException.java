package br.com.pontoEletronico.exception;

public class PontoEletronicoException extends RuntimeException {

	private String atributo;

	public String getAtributo() {
		return atributo;
	}

	public PontoEletronicoException(String atributo, String message) {
		super(message);
		this.atributo = atributo;
	}

}
