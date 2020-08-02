package br.com.pontoEletronico.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pontoEletronico.dto.UsuarioDTO;
import br.com.pontoEletronico.model.Usuario;
import br.com.pontoEletronico.service.UsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UsuarioController.class)
public class UsuarioControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UsuarioService usuarioService;
	
	private List<Usuario> usuarios;
	
	private Usuario usuario;
	
	private UsuarioDTO usuarioDTO;
	
	private int idUsuario = 0;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void init() {
		usuarios = new ArrayList<>();
		usuario = new Usuario();
		usuarioDTO = new UsuarioDTO();
		
		usuario.setNome("Teste");
		usuario.setCpf("398.988.920-64");
		usuario.setEmail("teste@teste.com");
		usuario.setDataCadastro(LocalDate.now());
		
		usuarioDTO.setNome("Teste");
		usuarioDTO.setCpf("398.988.920-64");
		usuarioDTO.setEmail("teste@teste.com");
		
		usuarios.add(usuario);
	}
	
	@Test
	public void listarTest() throws Exception {
		when(usuarioService.listar()).thenReturn(usuarios);
		mockMvc.perform(get("/usuarios"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void consultarTest() throws Exception {
		when(usuarioService.buscar(Mockito.anyInt())).thenReturn(Optional.of(usuario));
		mockMvc.perform(get("/usuarios/"+idUsuario))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(0))
				.andExpect(jsonPath("$.nome").value("Teste"))
				.andExpect(jsonPath("$.cpf").value("398.988.920-64"))
				.andExpect(jsonPath("$.email").value("teste@teste.com"))
				.andExpect(jsonPath("$.dataCadastro").value(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)));

	}
	
	@Test
	public void criarTest() throws Exception {
		usuarioDTO.setDataCadastro(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
		String usuarioJson = mapper.writeValueAsString(usuarioDTO);
		
		when(usuarioService.criar(Mockito.any(Usuario.class))).thenReturn(usuario);
		mockMvc.perform(post("/usuarios")
				.contentType(MediaType.APPLICATION_JSON)
				.content(usuarioJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(0))
				.andExpect(jsonPath("$.nome").value("Teste"))
				.andExpect(jsonPath("$.cpf").value("398.988.920-64"))
				.andExpect(jsonPath("$.email").value("teste@teste.com"));

	}
	
	@Test
	public void editarTest() throws Exception {
		String usuarioJson = mapper.writeValueAsString(usuarioDTO);
		
		when(usuarioService.editar(Mockito.anyInt(), Mockito.any(UsuarioDTO.class))).thenReturn(usuario);
		
		mockMvc.perform(patch("/usuarios/" + idUsuario)
				.contentType(MediaType.APPLICATION_JSON)
				.content(usuarioJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(0))
				.andExpect(jsonPath("$.nome").value("Teste"))
				.andExpect(jsonPath("$.cpf").value("398.988.920-64"))
				.andExpect(jsonPath("$.email").value("teste@teste.com"));
	}
	

	
}
