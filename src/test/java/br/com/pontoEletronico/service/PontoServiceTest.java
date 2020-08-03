package br.com.pontoEletronico.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import br.com.pontoEletronico.dto.ConsultaPontosDTO;
import br.com.pontoEletronico.dto.PontoDTO;
import br.com.pontoEletronico.enums.TipoBatida;
import br.com.pontoEletronico.model.Ponto;
import br.com.pontoEletronico.model.Usuario;
import br.com.pontoEletronico.repository.PontoRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PontoService.class)
public class PontoServiceTest {
	
	@Autowired
	private PontoService pontoService;
	
	@MockBean
	private PontoRepository pontoRepository;
	
	@MockBean
	private UsuarioService usuarioService;
	
	private Ponto ponto;
	
	private PontoDTO pontoDTO;
	
	private ConsultaPontosDTO consultaPontosDTO;
	
	private Usuario usuario;
	
	private List<Ponto> listaPontos;
	
	private int idUsuario = 0;
	
	@Before
	public void init() {
		ponto = new Ponto();
		pontoDTO = new PontoDTO();
		usuario = new Usuario();
		listaPontos = new ArrayList<Ponto>();
		consultaPontosDTO = new ConsultaPontosDTO();
		
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
		
		consultaPontosDTO.setListagemPonto(listaPontos);
		consultaPontosDTO.setHorasTrabalhadas(Ponto.getHorasTotais(listaPontos));
	}
	
	@Test
	public void criarTest() {
		when(pontoRepository.save(Mockito.any(Ponto.class))).then(answer -> answer.getArgument(0));
		when(usuarioService.buscar(Mockito.anyInt())).thenReturn(Optional.of(usuario));

		Ponto pontoCriado = pontoService.criar(ponto);

		assertEquals(ponto, pontoCriado);
	}
	
	@Test(expected = RuntimeException.class)
	public void criarExcecaoTest() {
		ponto.setId(20);
		pontoService.criar(ponto);
	}
	
	@Test
	public void consultarPorUsuarioTest() {
		when(pontoRepository.findByUsuario(Mockito.any(Usuario.class))).thenReturn(listaPontos);
		when(usuarioService.buscar(Mockito.anyInt())).thenReturn(Optional.of(usuario));

		Iterable<Ponto> pontosIterable = pontoService.consultarPorUsuario(idUsuario);
		List<Ponto> pontosEncontrados = Lists.newArrayList(pontosIterable);

		assertEquals(1, pontosEncontrados.size());
		assertEquals(ponto, pontosEncontrados.get(0));
	}
	
	@Test(expected = RuntimeException.class)
	public void consultarPorUsuarioExcecaoTest() {
		pontoService.consultarPorUsuario(20);
	}

	
	
	
}
