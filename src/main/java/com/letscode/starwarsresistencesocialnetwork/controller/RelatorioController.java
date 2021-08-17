package com.letscode.starwarsresistencesocialnetwork.controller;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.letscode.starwarsresistencesocialnetwork.model.RequestNegociar;
import com.letscode.starwarsresistencesocialnetwork.model.RequestRebelde;

@RequestMapping("rebelde")
@RestController
public class RelatorioController {

	/***
	 * Porcentagem de traidores.
	 * 
	 * GET /porcentagem/traidores
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/porcentagem/traidores")
	public ResponseEntity<Double> porcentagemTraidores(){
		
		System.out.println("");
		
		return new ResponseEntity<Double>(10.0 , HttpStatus.OK);
		
	}
	
	/***
	 * Porcentagem de rebeldes.
	 * 
	 * GET /porcentagem/rebeldes
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/porcentagem/rebeldes")
	public ResponseEntity<Double> porcentagemRebeldes(){
		
		System.out.println("");
		
		return new ResponseEntity<Double>(10.0 , HttpStatus.OK);
		
	}
	
	/***
	 * Quantidade média de cada tipo de recurso por rebelde (Ex: 2 armas por rebelde).
	 * 
	 * GET /quantidade/media/recursos
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/quantidade/media/recursoss")
	public ResponseEntity<Double> quantidadeMediaRecursosPorRebelde(){
		
		System.out.println("");
		
		return new ResponseEntity<Double>(10.0 , HttpStatus.OK);
		
	}
	
	/***
	 * Pontos perdidos devido a traidores.
	 * 
	 * GET /pontos/perdidos
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/pontos/perdidos")
	public ResponseEntity<Double> pontosPerdidosPorTraidores(){
		
		System.out.println("");
		
		return new ResponseEntity<Double>(10.0 , HttpStatus.OK);
		
	}
	
}
