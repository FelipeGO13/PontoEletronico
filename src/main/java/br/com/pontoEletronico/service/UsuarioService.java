package br.com.pontoEletronico.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontoEletronico.dto.UsuarioEdicaoDTO;
import br.com.pontoEletronico.exception.PontoEletronicoException;
import br.com.pontoEletronico.model.Usuario;
import br.com.pontoEletronico.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario criar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public Optional<Usuario> buscar(int id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);

		if (!usuario.isPresent()) {
			throw new PontoEletronicoException("Id", "Usuário não encontrado");
		}

		return usuario;
	}

	public Iterable<Usuario> listar() {
		return usuarioRepository.findAll();
	}

	public Usuario editar(int id, UsuarioEdicaoDTO usuario) throws Exception {

		Optional<Usuario> usuarioSelecionado = usuarioRepository.findById(id);

		if (!usuarioSelecionado.isPresent()) {
			throw new PontoEletronicoException("Id", "Usuário selecionado para edição não encontrado");
		}

		usuarioSelecionado.get().setNome(Optional.ofNullable(usuario.getNome()).isPresent() ? usuario.getNome()
				: usuarioSelecionado.get().getNome());
		usuarioSelecionado.get().setCpf(Optional.ofNullable(usuario.getCpf()).isPresent() ? usuario.getCpf()
				: usuarioSelecionado.get().getCpf());
		usuarioSelecionado.get().setEmail(Optional.ofNullable(usuario.getEmail()).isPresent() ? usuario.getEmail()
				: usuarioSelecionado.get().getEmail());

		return usuarioRepository.save(usuarioSelecionado.get());
	}

}