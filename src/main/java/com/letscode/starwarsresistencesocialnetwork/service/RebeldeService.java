package com.letscode.starwarsresistencesocialnetwork.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.letscode.starwarsresistencesocialnetwork.config.RecursoPoints;
import com.letscode.starwarsresistencesocialnetwork.model.RequestLocalizacao;
import com.letscode.starwarsresistencesocialnetwork.model.RequestNegociacao;
import com.letscode.starwarsresistencesocialnetwork.model.RequestRebelde;
import com.letscode.starwarsresistencesocialnetwork.model.RequestRecurso;
import com.letscode.starwarsresistencesocialnetwork.model.dao.Localizacao;
import com.letscode.starwarsresistencesocialnetwork.model.dao.Negociacao;
import com.letscode.starwarsresistencesocialnetwork.model.dao.Rebelde;
import com.letscode.starwarsresistencesocialnetwork.model.dao.Recurso;
import com.letscode.starwarsresistencesocialnetwork.repository.RebeldeRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class RebeldeService {

	@Autowired
	RebeldeRepository rebeldeRepository;
	
	@Autowired
	RecursoPoints recursoPoints;
	
	@Value("${star-wars.max-reporte:3}")
	private Integer maxReporte;
	
	/***
	 * Busca todos os rebeldes
	 * 
	 * @return List<Rebeldes>
	 */
	public List<Rebelde> findAllRebeldes(){
		
		List<Rebelde> rebeldes = rebeldeRepository.findAll();
		
		if (rebeldes.isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}else {
			return rebeldeRepository.findAll();
		}
	}

	/***
	 * Busca rebeldes por id
	 * 
	 * @param id
	 * @return Rebeldes
	 */
	public Rebelde findRebelde(Long id) {
		
		Optional<Rebelde> rebelde = rebeldeRepository.findById(id);
		
		if (rebelde.isPresent()) {
			return rebelde.get();
		}else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		
	}

	/***
	 * Cria novo rebelde
	 * 
	 * @param RequestRebelde
	 * @return Rebelde
	 */
	public Rebelde novoRebelde(RequestRebelde rebelde) {
		
		Rebelde rebeldeDao = rebeldeDTO(rebelde);
		try {
			rebeldeDao = rebeldeRepository.save(rebeldeDao);
			rebeldeDao.setRecursos(recursosDTO(rebelde.getRecursos(), rebeldeDao));
			rebeldeDao = rebeldeRepository.save(rebeldeDao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rebeldeDao;
	}
	
	/***
	 * Atualiza localizacao do rebelde
	 * 
	 * @param reqRebelde
	 * @return rebelde
	 */
	public Rebelde atualizaLocalizacao(RequestRebelde reqRebelde) {
		
		Optional<Rebelde> rebeldeOp = rebeldeRepository.findById(reqRebelde.getId());
		Rebelde rebelde = null;
		
		if (rebeldeOp.isPresent()) {
			rebelde = rebeldeOp.get();
			
			rebelde.setLocalizacao(localizacaoDTO(reqRebelde.getLocalizacao(), rebelde.getLocalizacao().getId()));
			
			rebelde = rebeldeRepository.save(rebelde);
		}else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		
		return rebelde;
	}
	
	/***
	 * Reportar um traidor pelo ID. Soma uma unidade no reporte. 
	 * Se o rebelde foi reportado mais de x vezes (x configurado no arquivo application.yml) ele eh confirmado como traidor, atualizando o campo traidor para true tambem.
	 * 
	 * @param id
	 * @return rebelde
	 */
	public Rebelde reportarTraidor(Long id) {
		
		Optional<Rebelde> rebeldeOp = rebeldeRepository.findById(id);
		Rebelde rebelde = null;
		
		if (rebeldeOp.isPresent()) {
			rebelde = rebeldeOp.get();
			rebelde.setReporte(rebelde.getReporte() + 1);
			
			if (rebelde.getReporte() >= maxReporte) {
				rebelde.setTraidor(true);
			}
			
			rebelde = rebeldeRepository.save(rebelde);
			
		}else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		
		return rebelde;
		
	}
	
	
	/***
	 * Atualiza a flag de traidor pelo ID.
	 * 
	 * @param reqRebelde
	 * @return rebelde
	 */
	public Rebelde atualizarTraidor(RequestRebelde reqRebelde) {
		
		Optional<Rebelde> rebeldeOp = rebeldeRepository.findById(reqRebelde.getId());
		Rebelde rebelde = null;
		
		if (rebeldeOp.isPresent()) {
			rebelde = rebeldeOp.get();
			
			rebelde.setTraidor(reqRebelde.isTraidor());
			
			rebelde = rebeldeRepository.save(rebelde);
			
		}else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		
		return rebelde;
		
	}
	
	/***
	 * Negociar itens.
	 * 
	 * Os rebeldes poderão negociar itens entre eles.
	 * Para isso, eles devem respeitar a tabela de preços, onde o valor do item é descrito em termo de pontos.
	 * 
	 * Ambos os lados deverão oferecer a mesma quantidade de pontos
	 * 
	 * Uma vez marcado como traidor, os itens do inventário se tornam inacessíveis (eles não podem ser negociados com os demais).
	 * 
	 * @param reqNegociar
	 * @return reqNegociar atualizado
	 */
	
	public Negociacao negociaItens(RequestNegociacao reqNegociar) {
		
		
		Negociacao negociar = null;
		
		int pontosRebelde1 = somaPontosRecursos(reqNegociar.getRebelde1());
		int pontosRebelde2 = somaPontosRecursos(reqNegociar.getRebelde2());
		
		if (pontosRebelde1 == pontosRebelde2) {
			negociar = efetuarTroca(reqNegociar.getRebelde1(), reqNegociar.getRebelde2());
		}else {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Soma de pontos dos rebeldes diferente. Nao eh possivel realizar a negociacao. Rebelde 1 com " + pontosRebelde1 + " pontos e Rebelde 2 com " + pontosRebelde2 + " pontos");
		}
		
		return negociar;
	}
	
	

	private Negociacao efetuarTroca(RequestRebelde reqRebelde1, RequestRebelde reqRebelde2) {
		
		
		
		Optional<Rebelde> rebelde1Op = rebeldeRepository.findById(reqRebelde1.getId());
		Optional<Rebelde> rebelde2Op = rebeldeRepository.findById(reqRebelde2.getId());
		
		Rebelde rebelde1 = null;
		Rebelde rebelde2 = null;
		if (rebelde1Op.isPresent()) {
			rebelde1 = rebelde1Op.get();
		}else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		
		if (rebelde2Op.isPresent()) {
			rebelde2 = rebelde2Op.get();
		}else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		
		if (rebelde1.isTraidor()) {
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Rebelde "+ rebelde1.getNome() + " eh um traidor! Nao faca a negociacao!");
		}
		if (rebelde2.isTraidor()) {
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Rebelde "+ rebelde2.getNome() + " eh um traidor! Nao faca a negociacao!");
		}
		
		
		rebelde1 = substraiRecursoFromRebelde(rebelde1, reqRebelde1);
		rebelde1 = somaRecursoFromRebelde(rebelde1, reqRebelde2);
		
		rebelde2 = substraiRecursoFromRebelde(rebelde2, reqRebelde2);
		rebelde2 = somaRecursoFromRebelde(rebelde2, reqRebelde1);
		
		return new Negociacao(rebeldeRepository.save(rebelde1), rebeldeRepository.save(rebelde2));
		
	}
	
	private Rebelde substraiRecursoFromRebelde(Rebelde rebelde, RequestRebelde reqRebelde) {
		for(RequestRecurso recurso : reqRebelde.getRecursos()) {
			if(rebelde.getRecursos().containsKey(recurso.getNome())){
				Long quantidadeFinal = rebelde.getRecursos().get(recurso.getNome()).getQuantidade() - recurso.getQuantidade();
				if (quantidadeFinal >= 0) {
					rebelde.getRecursos().get(recurso.getNome()).setQuantidade(quantidadeFinal);
				}else {
					throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Falta de recursos " + recurso.getNome() + " no rebelde " + rebelde.getNome() + ". Tentativa de trocar " + recurso.getQuantidade() + " porem possui apenas " + rebelde.getRecursos().get(recurso.getNome()).getQuantidade());
				}
				
			}else {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Recurso " + recurso.getNome() + " nao encontrado para o Rebelde " + rebelde.getNome());
			}
		}
		
		return rebelde;
	}
	
	private Rebelde somaRecursoFromRebelde(Rebelde rebelde, RequestRebelde reqRebelde) {
		for(RequestRecurso recurso : reqRebelde.getRecursos()) {
			if(rebelde.getRecursos().containsKey(recurso.getNome())){
				Long quantidadeFinal = rebelde.getRecursos().get(recurso.getNome()).getQuantidade() + recurso.getQuantidade();
				rebelde.getRecursos().get(recurso.getNome()).setQuantidade(quantidadeFinal);				
			}else {
				throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Recurso " + recurso.getNome() + " nao encontrado para o Rebelde " + rebelde.getNome());
			}
		}
		
		return rebelde;
	}


	private int somaPontosRecursos(RequestRebelde rebelde) {
		int soma = 0;
		for(RequestRecurso recurso : rebelde.getRecursos()) {
			int ponto = recursoPoints.getPoints(recurso.getNome()).intValue();
			int quantidade = recurso.getQuantidade().intValue();
			soma = soma + (ponto * quantidade);
		}
		return soma;
	}

	private Rebelde rebeldeDTO(RequestRebelde rebelde) {
		
		Rebelde rebeldeDao = new Rebelde();
		rebeldeDao.setGenero(rebelde.getGenero());
		rebeldeDao.setIdade(rebelde.getIdade());
		rebeldeDao.setNome(rebelde.getNome());
		rebeldeDao.setTraidor(rebelde.isTraidor());
		rebeldeDao.setLocalizacao(localizacaoDTO(rebelde.getLocalizacao(), null));

		return rebeldeDao;
	}
	
	private Localizacao localizacaoDTO(RequestLocalizacao reqLocalizacao, Long id) {
		Localizacao localizacao = new Localizacao();
		localizacao.setId(id);
		localizacao.setLatitude(reqLocalizacao.getLatitude());
		localizacao.setLongitude(reqLocalizacao.getLongitude());
		localizacao.setNome(reqLocalizacao.getNome());
		
		return localizacao;
	}
	
	private Map<String, Recurso> recursosDTO(List<RequestRecurso> reqRecursos, Rebelde rebelde){
		Map<String, Recurso> recursos = new HashMap<String, Recurso>();
		for (RequestRecurso reqRecurso : reqRecursos) {
			Recurso recurso = new Recurso();
			recurso.setNome(reqRecurso.getNome());
			recurso.setPontos(recursoPoints.getPoints(reqRecurso.getNome()));
			recurso.setQuantidade(reqRecurso.getQuantidade());
			recurso.setRebelde(rebelde);
			recursos.put(reqRecurso.getNome(), recurso);
		}
		
		return recursos;
	}
	
}
