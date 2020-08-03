package br.com.pontoEletronico.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<?> cadastrar(@PathVariable("idUsuario") int idUsuario, @Valid @RequestBody PontoDTO pontoDTO)
			throws Exception {

		Ponto ponto = new Ponto();
		Optional<Usuario> usuarioInformado = usuarioService.buscar(idUsuario);

		if (!Ponto.isTipoValido(pontoDTO.getTipoBatida())) {
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

		return ResponseEntity.ok(pontoService.consultarPorUsuario(idUsuario));
	}

}
