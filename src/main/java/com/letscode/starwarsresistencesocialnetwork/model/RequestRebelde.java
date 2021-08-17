package com.letscode.starwarsresistencesocialnetwork.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class RequestRebelde {
	
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("nome")
	private String nome;
	
	@JsonProperty("idade")
	private Integer idade;
	
	@JsonProperty("genero")
	private String genero;
	
	@JsonProperty("traidor")
	private Boolean traidor;
	
	@JsonProperty("localizacao")
	private RequestLocalizacao localizacao;
	
	@JsonProperty("recursos")
	private List<RequestRecurso> recursos;
	
}
