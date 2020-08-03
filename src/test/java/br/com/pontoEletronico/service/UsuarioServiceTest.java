package br.com.pontoEletronico.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.pontoEletronico.model.Usuario;
import br.com.pontoEletronico.repository.UsuarioRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = UsuarioService.class)
public class UsuarioServiceTest {

	@Autowired
	private UsuarioService usuarioService;

	@MockBean
	private UsuarioRepository usuarioRepository;

	private List<Usuario> usuarios;

	private Usuario usuario;
	
	private int idUsuario = 0;

	@Before
	public void init() {
		usuarios = new ArrayList<>();
		usuario = new Usuario();

		usuario.setNome("Teste");
		usuario.setCpf("398.988.920-64");
		usuario.setEmail("teste@teste.com");
		usuario.setDataCadastro(LocalDate.now());
		
		usuarios.add(usuario);
	}

	@Test
	public void listarTest() {
		when(usuarioRepository.findAll()).thenReturn(usuarios);

		Iterable<Usuario> usuariosIterable = usuarioService.listar();
		List<Usuario> usuariosEncontrados = Lists.newArrayList(usuariosIterable);

		assertEquals(1, usuariosEncontrados.size());
		assertEquals(usuario, usuariosEncontrados.get(0));
	}

	@Test
	public void consultarPorIdTest() {
		when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(usuario));

		Optional<Usuario> usuarioEncontrado = usuarioService.buscar(idUsuario);

		assertEquals(usuario, usuarioEncontrado.get());
	}

	@Test
	public void criarTest() {
		when(usuarioRepository.save(Mockito.any(Usuario.class))).then(answer -> answer.getArgument(0));

		Usuario usuarioCriado = usuarioService.criar(usuario);

		assertEquals(usuario, usuarioCriado);
	}

	@Test
	public void editarTest() throws Exception {
		when(usuarioRepository.save(Mockito.any(Usuario.class))).then(answer -> answer.getArgument(0));
		when(usuarioRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(usuario));

		Usuario usuarioEditado = usuarioService.editar(idUsuario, usuario);

		assertEquals(usuario.getNome(), usuarioEditado.getNome());
		assertEquals(usuario.getEmail(), usuarioEditado.getEmail());
		assertEquals(usuario.getCpf(), usuarioEditado.getCpf());
	}
	
	@Test(expected = RuntimeException.class)
	public void editarExcecaoTest() throws Exception {
		usuarioService.editar(20, usuario);
	}

}
