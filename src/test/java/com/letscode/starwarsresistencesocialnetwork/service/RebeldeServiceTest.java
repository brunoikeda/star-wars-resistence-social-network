package com.letscode.starwarsresistencesocialnetwork.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.letscode.starwarsresistencesocialnetwork.model.RequestLocalizacao;
import com.letscode.starwarsresistencesocialnetwork.model.RequestNegociacao;
import com.letscode.starwarsresistencesocialnetwork.model.RequestRebelde;
import com.letscode.starwarsresistencesocialnetwork.model.RequestRecurso;
import com.letscode.starwarsresistencesocialnetwork.model.dao.Negociacao;
import com.letscode.starwarsresistencesocialnetwork.model.dao.Rebelde;
import com.letscode.starwarsresistencesocialnetwork.repository.RebeldeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration
public class RebeldeServiceTest {

	@Autowired
	RebeldeService service;
	
	@Autowired
	RebeldeRepository rebeldeRepository;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testCriaNovoRebelde_sucesso() {
		
		String nome = "nome";
		String local = "local";
		String lat = "1";
		String lon = "2";

		// Cria um rebelde
		List<RequestRecurso> recursos = new ArrayList<RequestRecurso>();
		recursos.add(new RequestRecurso(null, "arma", 1l, null));
		RequestRebelde reqRebelde = criaRequestRebelde(null, nome, criaRequestLocalizacao(local, lat, lon), recursos);
		Rebelde rebelde = service.novoRebelde(reqRebelde);
		
		assertNotNull(rebelde);
		assertEquals(nome, rebelde.getNome());
		assertEquals(local, rebelde.getLocalizacao().getNome());
		assertEquals(lat, rebelde.getLocalizacao().getLatitude());
		assertEquals(lon, rebelde.getLocalizacao().getLongitude());
		assertFalse(rebelde.getRecursos().isEmpty());
		assertEquals(1l, rebelde.getRecursos().get("arma").getQuantidade());
		
		Optional<Rebelde> rebeldeDAO = rebeldeRepository.findById(rebelde.getId());
		assertTrue(rebeldeDAO.isPresent());
		
	}
	
	@Test
	public void testAtualizaLocalizacao_sucesso() {
		
		String nome = "nome";
		String localOrigem = "local";
		String latOrigem = "1";
		String lonOrigem = "2";
		
		String localAtualizado = "local";
		String latAtualizado = "1";
		String lonAtualizado = "2";

		// Cria um rebelde
		List<RequestRecurso> recursos = new ArrayList<RequestRecurso>();
		recursos.add(new RequestRecurso(null, "arma", 1l, null));
		RequestRebelde reqRebelde = criaRequestRebelde(null, nome, criaRequestLocalizacao(localOrigem, latOrigem, lonOrigem), recursos);
		Rebelde rebelde = service.novoRebelde(reqRebelde);
		
		// Assert
		Optional<Rebelde> rebeldeDAO = rebeldeRepository.findById(rebelde.getId());
		assertTrue(rebeldeDAO.isPresent());
		assertEquals(localOrigem, rebeldeDAO.get().getLocalizacao().getNome());
		assertEquals(latOrigem, rebeldeDAO.get().getLocalizacao().getLatitude());
		assertEquals(lonOrigem, rebeldeDAO.get().getLocalizacao().getLongitude());
		
		reqRebelde.setLocalizacao(criaRequestLocalizacao(localAtualizado, latAtualizado, lonAtualizado));
		reqRebelde.setId(rebelde.getId());
		rebelde = service.atualizaLocalizacao(reqRebelde);
		
		rebeldeDAO = rebeldeRepository.findById(rebelde.getId());
		assertTrue(rebeldeDAO.isPresent());
		assertEquals(localAtualizado, rebeldeDAO.get().getLocalizacao().getNome());
		assertEquals(latAtualizado, rebeldeDAO.get().getLocalizacao().getLatitude());
		assertEquals(lonAtualizado, rebeldeDAO.get().getLocalizacao().getLongitude());		
		
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void testAtualizaLocalizacao_rebeldeNotFound() {
		
		rebeldeRepository.deleteAll();
		
		String nome = "nome";
		String localOrigem = "local";
		String latOrigem = "1";
		String lonOrigem = "2";
		
		// Cria um rebelde
		List<RequestRecurso> recursos = new ArrayList<RequestRecurso>();
		recursos.add(new RequestRecurso(null, "arma", 1l, null));
		RequestRebelde reqRebelde = criaRequestRebelde(1l, nome, criaRequestLocalizacao(localOrigem, latOrigem, lonOrigem), recursos);
		
		service.atualizaLocalizacao(reqRebelde);
		
	}
	
	@Test
	public void testReportaTraidor_sucesso() {
		
		String nome = "nome";
		String localOrigem = "local";
		String latOrigem = "1";
		String lonOrigem = "2";
		
		// Cria um rebelde
		List<RequestRecurso> recursos = new ArrayList<RequestRecurso>();
		recursos.add(new RequestRecurso(null, "arma", 1l, null));
		RequestRebelde reqRebelde = criaRequestRebelde(null, nome, criaRequestLocalizacao(localOrigem, latOrigem, lonOrigem), recursos);
		Rebelde rebelde = service.novoRebelde(reqRebelde);
		
		// Assert pre exec
		Optional<Rebelde> rebeldeDAO = rebeldeRepository.findById(rebelde.getId());
		assertTrue(rebeldeDAO.isPresent());
		assertEquals(0, rebeldeDAO.get().getReporte());
		
		// Exec
		rebelde = service.reportarTraidor(rebeldeDAO.get().getId());
		
		// Assert pos exec
		rebeldeDAO = rebeldeRepository.findById(rebelde.getId());
		assertTrue(rebeldeDAO.isPresent());
		assertEquals(1, rebeldeDAO.get().getReporte());		
		
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void testReporteTraidor_rebeldeNotFound() {
		
		rebeldeRepository.deleteAll();

		service.reportarTraidor(1l);
		
	}
	
	@Test
	public void testAtualizaTraidor_sucesso() {
		
		String nome = "nome";
		String localOrigem = "local";
		String latOrigem = "1";
		String lonOrigem = "2";
		
		// Cria um rebelde
		List<RequestRecurso> recursos = new ArrayList<RequestRecurso>();
		recursos.add(new RequestRecurso(null, "arma", 1l, null));
		RequestRebelde reqRebelde = criaRequestRebelde(null, nome, criaRequestLocalizacao(localOrigem, latOrigem, lonOrigem), recursos);
		Rebelde rebelde = service.novoRebelde(reqRebelde);
		
		// Assert pre exec
		Optional<Rebelde> rebeldeDAO = rebeldeRepository.findById(rebelde.getId());
		assertTrue(rebeldeDAO.isPresent());
		assertFalse(rebeldeDAO.get().isTraidor());
		
		// Exec
		reqRebelde.setTraidor(true);
		reqRebelde.setId(rebelde.getId());
		rebelde = service.atualizarTraidor(reqRebelde);
		
		// Assert pos exec
		rebeldeDAO = rebeldeRepository.findById(rebelde.getId());
		assertTrue(rebeldeDAO.isPresent());
		assertTrue(rebeldeDAO.get().isTraidor());		
		
	}
	
	@Test(expected = HttpClientErrorException.class)
	public void testAtualizaTraidor_rebeldeNotFound() {
		
		rebeldeRepository.deleteAll();
		
		String nome = "nome";
		String localOrigem = "local";
		String latOrigem = "1";
		String lonOrigem = "2";
		
		// Cria um rebelde
		List<RequestRecurso> recursos = new ArrayList<RequestRecurso>();
		recursos.add(new RequestRecurso(null, "arma", 1l, null));
		RequestRebelde reqRebelde = criaRequestRebelde(1l, nome, criaRequestLocalizacao(localOrigem, latOrigem, lonOrigem), recursos);

		service.atualizarTraidor(reqRebelde);
		
	}
	
	
	private RequestRebelde criaRequestRebelde(Long id, String nome, RequestLocalizacao localizacao, List<RequestRecurso> recursos) {
		RequestRebelde rebelde = new RequestRebelde();
		rebelde.setId(id);
		rebelde.setNome(nome);
		rebelde.setGenero("masculino");
		rebelde.setIdade(10);
		rebelde.setLocalizacao(localizacao);
		rebelde.setRecursos(recursos);
		rebelde.setTraidor(false);
		
		return rebelde;
	}
	
	private RequestLocalizacao criaRequestLocalizacao(String nome, String latitude, String longitude) {
		RequestLocalizacao local = new RequestLocalizacao();
		local.setNome(nome);
		local.setLatitude(latitude);
		local.setLongitude(longitude);
		return local;
	}

}
