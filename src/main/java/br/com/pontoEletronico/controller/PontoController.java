package br.com.pontoEletronico.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.pontoEletronico.dto.ConsultaPontosDTO;
import br.com.pontoEletronico.dto.PontoDTO;
import br.com.pontoEletronico.enums.TipoBatida;
import br.com.pontoEletronico.model.Ponto;
import br.com.pontoEletronico.model.Usuario;
import br.com.pontoEletronico.service.PontoService;
import br.com.pontoEletronico.service.UsuarioService;

@RestController
@RequestMapping("/batidas-ponto")
public class PontoController {

	@Autowired
	private PontoService pontoService;

	@Autowired
	UsuarioService usuarioService;

	@PostMapping("/{idUsuario}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<?> cadastrar(@PathVariable("idUsuario") int idUsuario, @Valid @RequestBody PontoDTO pontoDTO)
			throws Exception {

		Ponto ponto = new Ponto();
		Optional<Usuario> usuarioInformado = usuarioService.buscar(idUsuario);

		if (!pontoDTO.getTipoBatida().toUpperCase().equals(TipoBatida.ENTRADA.name())
				&& !pontoDTO.getTipoBatida().toUpperCase().equals(TipoBatida.SAIDA.name())) {
			return new ResponseEntity<>("Tipo de Batida deve ser ENTRADA ou SAIDA", HttpStatus.BAD_REQUEST);
		}

		ponto.setUsuario(usuarioInformado.get());
		ponto.setTipoBatida(TipoBatida.valueOf(pontoDTO.getTipoBatida().toUpperCase()));
		ponto.setDataHoraBatida(LocalDateTime.parse(pontoDTO.getDataHoraBatida()));

		return new ResponseEntity<>(pontoService.criar(ponto), HttpStatus.CREATED);
	}

	@GetMapping("/{idUsuario}")
	public ResponseEntity<ConsultaPontosDTO> consultarPorUsuario(@PathVariable("idUsuario") int idUsuario)
			throws Exception {

		Iterable<Ponto> listagemPontos = pontoService.consultarPorUsuario(idUsuario);

		Duration horasTrabalhadasTotais = Duration.ofHours(0);

		for (Ponto p : listagemPontos) {
			horasTrabalhadasTotais = horasTrabalhadasTotais
					.plus(calcularHorasDiarias(p, listagemPontos, horasTrabalhadasTotais));
		}

		ConsultaPontosDTO consultaPontosDTO = new ConsultaPontosDTO();

		consultaPontosDTO.setListagemPonto(listagemPontos);
		consultaPontosDTO.setHorasTrabalhadas(String.format("%d:%02d:%02d", horasTrabalhadasTotais.toHours(),
				horasTrabalhadasTotais.toMinutesPart(), horasTrabalhadasTotais.toSecondsPart()));

		return ResponseEntity.ok(consultaPontosDTO);
	}

	private Duration calcularHorasDiarias(Ponto ponto, Iterable<Ponto> listagemPontos,
			Duration horasTrabalhadasTotais) {

		Optional<Ponto> saida = Optional.empty();

		List<Ponto> pontos = StreamSupport.stream(listagemPontos.spliterator(), false).collect(Collectors.toList());
		

		for(int i=0; i < pontos.size() - 1 ; i++) {
			if (pontos.get(i).getDataHoraBatida().isEqual(ponto.getDataHoraBatida()) && pontos.get(i).getTipoBatida().equals(TipoBatida.ENTRADA)) {
				saida = Optional.of(pontos.get(i+1));
				break;
			}
		}
		
		if (ponto.getTipoBatida().equals(TipoBatida.SAIDA) || !saida.isPresent() || saida.get().getTipoBatida().equals(TipoBatida.ENTRADA)) {
			return Duration.ofHours(0);
		}

		return Duration.between(ponto.getDataHoraBatida(), saida.get().getDataHoraBatida());

	}

}
