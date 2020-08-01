package br.com.pontoEletronico.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
	
	public static Boolean isTipoValido(String tipoBatida) {
		return tipoBatida.toUpperCase().equals(TipoBatida.ENTRADA.name())
				&& tipoBatida.toUpperCase().equals(TipoBatida.SAIDA.name());
	}
	
	public static String getHorasTotais(Iterable<Ponto> listagemPontos) {
		Duration horasTrabalhadasTotais = Duration.ofHours(0);
		
		for (Ponto p : listagemPontos) {
			horasTrabalhadasTotais = horasTrabalhadasTotais
					.plus(getHorasDiarias(p, listagemPontos, horasTrabalhadasTotais));
		}
		
		return String.format("%d:%02d:%02d", horasTrabalhadasTotais.toHours(),
				horasTrabalhadasTotais.toMinutesPart(), horasTrabalhadasTotais.toSecondsPart());
	}

	public static Duration getHorasDiarias(Ponto ponto, Iterable<Ponto> listagemPontos, Duration horasTrabalhadasTotais) {
		Optional<Ponto> saida = Optional.empty();
		List<Ponto> pontos = StreamSupport.stream(listagemPontos.spliterator(), false).collect(Collectors.toList());

		for (int i = 0; i < pontos.size() - 1; i++) {
			if (pontos.get(i).getDataHoraBatida().isEqual(ponto.getDataHoraBatida())
					&& pontos.get(i).getTipoBatida().equals(TipoBatida.ENTRADA)) {
				saida = Optional.of(pontos.get(i + 1));
				break;
			}
		}

		if (ponto.getTipoBatida().equals(TipoBatida.SAIDA) || !saida.isPresent()
				|| saida.get().getTipoBatida().equals(TipoBatida.ENTRADA)) {
			return Duration.ofHours(0);
		}

		return Duration.between(ponto.getDataHoraBatida(), saida.get().getDataHoraBatida());

	}


}
