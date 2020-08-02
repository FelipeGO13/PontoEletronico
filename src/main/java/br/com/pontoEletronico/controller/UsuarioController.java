package br.com.pontoEletronico.controller;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pontoEletronico.dto.UsuarioDTO;
import br.com.pontoEletronico.model.Usuario;
import br.com.pontoEletronico.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping
	public Iterable<Usuario> listar(){
		return usuarioService.listar();
	}
	
	@PostMapping(produces = "application/json;charset=UTF-8")
	public ResponseEntity<?> criar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
		Usuario usuario = new Usuario();
		
		if (!Optional.ofNullable(usuarioDTO.getDataCadastro()).isPresent()) {
			return new ResponseEntity<>("Data de cadastro deve ser preenchida", HttpStatus.BAD_REQUEST);
		}
		
		usuario.setNome(usuarioDTO.getNome());
		usuario.setCpf(usuarioDTO.getCpf());
		usuario.setEmail(usuarioDTO.getEmail());
		usuario.setDataCadastro(LocalDate.parse(usuarioDTO.getDataCadastro()));
		
		return new ResponseEntity<>(usuarioService.criar(usuario), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public Optional<Usuario> consultarPorId(@PathVariable int id){
		return usuarioService.buscar(id);
	}
	
	@PatchMapping("/{id}")
	public Usuario editar(@PathVariable("id") int id, @Valid @RequestBody UsuarioDTO usuario) throws Exception {
		return usuarioService.editar(id, usuario);
	}
}
