package com.letscode.starwarsresistencesocialnetwork.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.letscode.starwarsresistencesocialnetwork.model.RequestNegociacao;
import com.letscode.starwarsresistencesocialnetwork.model.RequestRebelde;
import com.letscode.starwarsresistencesocialnetwork.model.dao.Negociacao;
import com.letscode.starwarsresistencesocialnetwork.model.dao.Rebelde;
import com.letscode.starwarsresistencesocialnetwork.service.RebeldeService;

@RequestMapping("rebelde")
@RestController
public class RebeldeController {
	
	@Autowired
	RebeldeService rebeldeService;
	
	/***
	 * Busca todos os Rebeldes
	 * 
	 * GET /list
	 * 
	 * @return List<Rebelde>
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/list")
	public ResponseEntity<List<Rebelde>> findAllRebelde(){
		
		List<Rebelde> rebeldeList = rebeldeService.findAllRebeldes();
		
		return new ResponseEntity<List<Rebelde>>(rebeldeList, HttpStatus.OK);
		
	}
	
	/***
	 * Busca um rebelde especifico por ID
	 * 
	 * GET /{id}
	 * 
	 * @return List<Rebelde>
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<Rebelde> findRebelde(@PathVariable Long id){
		
		Rebelde rebelde = rebeldeService.findRebelde(id);
		
		return new ResponseEntity<Rebelde>(rebelde, HttpStatus.OK);
		
	}

	/***
	 * Adiciona um Rebelde
	 * Um rebelde deve ter um nome, idade, gênero, localização (latitude, longitude e nome, na galáxia, da base ao qual faz parte).
	 * Um rebelde também possui um inventário que deverá ser passado na requisição com os recursos em sua posse.
	 * 
	 * POST /novo
	 * 
	 * @return rebelde
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/novo")
	public ResponseEntity<Rebelde> novo(@RequestBody RequestRebelde reqRebelde){
		
		Rebelde rebelde = rebeldeService.novoRebelde(reqRebelde);
		
		return new ResponseEntity<Rebelde>(rebelde, HttpStatus.OK);
		
	}
	
	/***
	 * Atualiza a localizacao do Rebelde
	 * Um rebelde deve possuir a capacidade de reportar sua última localização, armazenando a nova latitude/longitude/nome (não é necessário rastrear as localizações, apenas sobrescrever a última é o suficiente).
	 * 
	 * POST /atualiza/localizacao
	 * 
	 * @param rebelde, latitude, longitude, nome localizacao
	 * @return rebelde
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/atualiza/localizacao")
	public ResponseEntity<Rebelde> atualizaLocalizacao(@RequestBody RequestRebelde reqRebelde){
		
		Rebelde rebelde = rebeldeService.atualizaLocalizacao(reqRebelde);
		
		return new ResponseEntity<Rebelde>(rebelde, HttpStatus.OK);
		
	}
	
	
	/***
	 * Reportar o rebelde como um traidor
	 * Eventualmente algum rebelde irá trair a resistência e se aliar ao império. Quando isso acontecer, nós precisamos informar que o rebelde é um traidor.
	 * Um traidor não pode negociar os recursos com os demais rebeldes, não pode manipular seu inventário, nem ser exibido em relatórios.
	 * 
	 * Um rebelde é marcado como traidor quando, ao menos, três outros rebeldes reportarem a traição.
	 * Uma vez marcado como traidor, os itens do inventário se tornam inacessíveis (eles não podem ser negociados com os demais).
	 * 
	 * GET /reportar/traidor/{id}
	 * 
	 * @param rebelde
	 * @return rebelde
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/reportar/traidor/{idRebelde}")
	public ResponseEntity<Rebelde> reportaTraidor(@PathVariable("idRebelde") Long id){
		
		Rebelde rebelde = rebeldeService.reportarTraidor(id);
		
		return new ResponseEntity<Rebelde>(rebelde, HttpStatus.OK);
		
	}
	
	/***
	 * Atualizar o rebelde como um traidor
	 * Eventualmente algum rebelde irá trair a resistência e se aliar ao império. Quando isso acontecer, nós precisamos informar que o rebelde é um traidor.
	 * Um traidor não pode negociar os recursos com os demais rebeldes, não pode manipular seu inventário, nem ser exibido em relatórios.
	 * 
	 * Um rebelde é marcado como traidor quando, ao menos, três outros rebeldes reportarem a traição.
	 * Uma vez marcado como traidor, os itens do inventário se tornam inacessíveis (eles não podem ser negociados com os demais).
	 * 
	 * POST /atualiza/traidor
	 * 
	 * @param rebelde
	 * @return rebelde
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/atualiza/traidor")
	public ResponseEntity<Rebelde> atualizaTraidor(@RequestBody RequestRebelde reqRebelde){
		
		Rebelde rebelde = rebeldeService.atualizarTraidor(reqRebelde);
		
		return new ResponseEntity<Rebelde>(rebelde, HttpStatus.OK);
		
	}
	
	/***
	 * Negociar Itens
	 * 
	 * Os rebeldes poderão negociar itens entre eles.
	 * Para isso, eles devem respeitar a tabela de preços abaixo, onde o valor do item é descrito em termo de pontos.
	 * Ambos os lados deverão oferecer a mesma quantidade de pontos. Por exemplo, 1 arma e 1 água (1 x 4 + 1 x 2) valem 6 comidas (6 x 1) ou 2 munições (2 x 3).
	 * A negociação em si não será armazenada, mas os itens deverão ser transferidos de um rebelde a outro.
	 * 
	 * Item			Pontos
	 * 1 Arma		4 pontos
	 * 1 Munição	3 pontos
	 * 1 Água		2 pontos
	 * 1 Comida		1 ponto
	 * 
	 * POST /negocia/item
	 * 
	 * @param rebelde
	 * @return rebelde
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/negocia/item")
	public ResponseEntity<Negociacao> negociarItens(@RequestBody RequestNegociacao reqNegociar){
		
		Negociacao negociar = rebeldeService.negociaItens(reqNegociar);
		
		return new ResponseEntity<Negociacao>(negociar, HttpStatus.OK);
		
	}
	
	
}
