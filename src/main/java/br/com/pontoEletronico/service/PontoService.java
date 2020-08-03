package br.com.pontoEletronico.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontoEletronico.dto.ConsultaPontosDTO;
import br.com.pontoEletronico.exception.PontoEletronicoException;
import br.com.pontoEletronico.model.Ponto;
import br.com.pontoEletronico.model.Usuario;
import br.com.pontoEletronico.repository.PontoRepository;

@Service
public class PontoService {

	@Autowired
	private PontoRepository pontoRepository;

	@Autowired
	private UsuarioService usuarioService;

	public Ponto criar(Ponto ponto) {

		Usuario usuario = ponto.getUsuario();

		Optional<Usuario> usuarioSelecionado = usuarioService.buscar(usuario.getId());

		if (!usuarioSelecionado.isPresent()) {
			throw new PontoEletronicoException("Id", "Usuário não encontrado");
		}

		return pontoRepository.save(ponto);

	}

	public ConsultaPontosDTO consultarPorUsuario(int idUsuario) {

		Optional<Usuario> usuarioSelecionado = usuarioService.buscar(idUsuario);

		if (!usuarioSelecionado.isPresent()) {
			throw new PontoEletronicoException("Id", "Usuário não encontrado");
		}
		
		Iterable<Ponto> listagemPontos = pontoRepository.findByUsuario(usuarioSelecionado.get());

		ConsultaPontosDTO consultaPontosDTO = new ConsultaPontosDTO();

		consultaPontosDTO.setListagemPonto(listagemPontos);
		consultaPontosDTO.setHorasTrabalhadas(Ponto.getHorasTotais(listagemPontos));
		
		return consultaPontosDTO;
	}

}
