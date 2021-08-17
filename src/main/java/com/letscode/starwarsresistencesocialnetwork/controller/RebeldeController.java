package com.letscode.starwarsresistencesocialnetwork.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.letscode.starwarsresistencesocialnetwork.model.Rebelde;

@RequestMapping("rebelde")
@RestController
public class RebeldeController {

	/***
	 * Adiciona um Rebelde
	 * Um rebelde deve ter um nome, idade, gênero, localização (latitude, longitude e nome, na galáxia, da base ao qual faz parte).
	 * Um rebelde também possui um inventário que deverá ser passado na requisição com os recursos em sua posse.
	 * @param rebelde
	 * @return rebelde
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/novo")
	public ResponseEntity<Rebelde> novo(@RequestBody Rebelde rebelde){
		
		System.out.println(rebelde);
		
		return new ResponseEntity<Rebelde>(rebelde, HttpStatus.OK);
		
	}
	
	/***
	 * Atualiza a localizacao do Rebelde
	 * Um rebelde deve possuir a capacidade de reportar sua última localização, armazenando a nova latitude/longitude/nome (não é necessário rastrear as localizações, apenas sobrescrever a última é o suficiente).
	 * @param rebelde, latitude, longitude, nome localizacao
	 * @return rebelde
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/novo")
	public ResponseEntity<Rebelde> atualizaLocalizacao(@RequestBody Rebelde rebelde){
		
		System.out.println(rebelde);
		
		return new ResponseEntity<Rebelde>(rebelde, HttpStatus.OK);
		
	}
	
	/***
	 * Atualiza a localizacao do Rebelde
	 * Um rebelde deve possuir a capacidade de reportar sua última localização, armazenando a nova latitude/longitude/nome (não é necessário rastrear as localizações, apenas sobrescrever a última é o suficiente).
	 * @param rebelde
	 * @return rebelde
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/novo")
	public ResponseEntity<Rebelde> novoRebelde(@RequestBody Rebelde rebelde){
		
		System.out.println(rebelde);
		
		return new ResponseEntity<Rebelde>(rebelde, HttpStatus.OK);
		
	}
	
}
