package br.com.pontoEletronico.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import br.com.pontoEletronico.dto.PontoDTO;
import br.com.pontoEletronico.enums.TipoBatida;
import br.com.pontoEletronico.model.Ponto;
import br.com.pontoEletronico.model.Usuario;
import br.com.pontoEletronico.service.PontoService;
import br.com.pontoEletronico.service.UsuarioService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PontoController.class)
public class PontoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PontoService pontoService;

	@MockBean
	private UsuarioService usuarioService;
	
	private Ponto ponto;
	
	private PontoDTO pontoDTO;
	
	private Usuario usuario;
	
	private int idUsuario = 0;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private List<Ponto> listaPontos;
	
	@Before
	public void init() {
		ponto = new Ponto();
		pontoDTO = new PontoDTO();
		usuario = new Usuario();
		listaPontos = new ArrayList<Ponto>();
		
		usuario.setNome("Teste");
		usuario.setCpf("398.988.920-64");
		usuario.setEmail("teste@teste.com");
		usuario.setDataCadastro(LocalDate.now());
		
		ponto.setId(0);
		ponto.setDataHoraBatida(LocalDateTime.parse("2020-07-10T09:10:10"));
		ponto.setTipoBatida(TipoBatida.ENTRADA);
		ponto.setUsuario(usuario);
		
		pontoDTO.setDataHoraBatida(LocalDateTime.parse("2020-07-10T09:10:10").toString());
		pontoDTO.setTipoBatida(TipoBatida.ENTRADA.name());
		
		listaPontos.add(ponto);
		
	}
	
	@Test
	public void cadastrarTest() throws Exception  {
		when(pontoService.criar(Mockito.any(Ponto.class))).thenReturn(ponto);
		when(usuarioService.buscar(Mockito.anyInt())).thenReturn(Optional.of(usuario));
		
		String pontoJson = mapper.writeValueAsString(pontoDTO);
		
		mockMvc.perform(post("/batidas-ponto/"+idUsuario)
				.contentType(MediaType.APPLICATION_JSON)
				.content(pontoJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(0))
				.andExpect(jsonPath("$.usuario.id").value(0))
				.andExpect(jsonPath("$.usuario.nome").value("Teste"))
				.andExpect(jsonPath("$.dataHoraBatida").value(LocalDateTime.parse("2020-07-10T09:10:10").format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
				.andExpect(jsonPath("$.tipoBatida").value(TipoBatida.ENTRADA.toString()));
	}
	
	@Test
	public void cadastrarErroTest() throws Exception  {
		when(pontoService.criar(Mockito.any(Ponto.class))).thenReturn(ponto);
		when(usuarioService.buscar(Mockito.anyInt())).thenReturn(Optional.of(usuario));
		
		pontoDTO.setTipoBatida("teste");
		String pontoJson = mapper.writeValueAsString(pontoDTO);
		
		mockMvc.perform(post("/batidas-ponto/"+idUsuario)
				.contentType(MediaType.APPLICATION_JSON)
				.content(pontoJson))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void consultarPorUsuarioTest() throws Exception {
		when(pontoService.consultarPorUsuario(Mockito.anyInt())).thenReturn(listaPontos);
		
		mockMvc.perform(get("/batidas-ponto/"+idUsuario))
				.andExpect(status().isOk());
	}

}
