package br.com.pontoEletronico.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.pontoEletronico.model.Ponto;
import br.com.pontoEletronico.model.Usuario;

public interface PontoRepository extends CrudRepository<Ponto, Integer> {
	Iterable<Ponto> findByUsuario(Usuario usuario);
}
