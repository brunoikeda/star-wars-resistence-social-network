package com.letscode.starwarsresistencesocialnetwork.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.letscode.starwarsresistencesocialnetwork.service.RelatorioService;

@RequestMapping("relatorio")
@RestController
public class RelatorioController {
	
	@Autowired
	RelatorioService relatorio;

	/***
	 * Porcentagem de traidores.
	 * 
	 * GET /porcentagem/traidores
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/porcentagem/traidores")
	public ResponseEntity<String> porcentagemTraidores(){
		
		String porcentagemTraidores = relatorio.porcentagemTraidores();
		
		return new ResponseEntity<String>(porcentagemTraidores , HttpStatus.OK);
		
	}
	
	/***
	 * Porcentagem de rebeldes.
	 * 
	 * GET /porcentagem/rebeldes
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/porcentagem/rebeldes")
	public ResponseEntity<String> porcentagemRebeldes(){
		
		String porcentagemRebeldes = relatorio.porcentagemRebeldes();
		
		return new ResponseEntity<String>(porcentagemRebeldes , HttpStatus.OK);
		
	}
	
	/***
	 * Quantidade média de cada tipo de recurso por rebelde (Ex: 2 armas por rebelde).
	 * 
	 * GET /quantidade/media/recursos
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/quantidade/media/recursos")
	public ResponseEntity<String> quantidadeMediaRecursosPorRebelde(){
		
		String mediaRecursos = relatorio.mediaRecursos();
		
		return new ResponseEntity<String>(mediaRecursos , HttpStatus.OK);
		
	}
	
	/***
	 * Pontos perdidos devido a traidores.
	 * 
	 * GET /pontos/perdidos
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/pontos/perdidos")
	public ResponseEntity<String> pontosPerdidosPorTraidores(){
		
		String pontosPerdidos = relatorio.pontosPerdidosPorTraidores();
		
		return new ResponseEntity<String>(pontosPerdidos , HttpStatus.OK);
		
	}
	
	/***
	 * Relatorio completo.
	 * 
	 * GET /completo
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/completo")
	public ResponseEntity<String> relatorioCompleto(){
		
		
		String porcentagemTraidores = relatorio.porcentagemTraidores();

		String porcentagemRebeldes = relatorio.porcentagemRebeldes();

		String mediaRecursos = relatorio.mediaRecursos();
		
		String pontosPerdidos = relatorio.pontosPerdidosPorTraidores();
		
		String relatorio = "### RELATORIO COMPLETO ###\n";
		relatorio = relatorio + porcentagemTraidores + porcentagemRebeldes + mediaRecursos + pontosPerdidos;
		
		return new ResponseEntity<String>(relatorio , HttpStatus.OK);
		
	}
	
}
