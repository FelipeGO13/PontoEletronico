package br.com.pontoEletronico.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.pontoEletronico.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

}
