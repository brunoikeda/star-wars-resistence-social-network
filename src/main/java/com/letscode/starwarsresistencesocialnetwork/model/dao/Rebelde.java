package com.letscode.starwarsresistencesocialnetwork.model.dao;


import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class Rebelde {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
	private Long id;
	
	@JsonProperty("nome")
	private String nome;
	
	@JsonProperty("idade")
	private Integer idade;
	
	@JsonProperty("genero")
	private String genero;
	
	@JsonProperty("reporte")
	private int reporte;
	
	@JsonProperty("traidor")
	private boolean traidor;
	
	@JsonProperty("localizacao")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "localizacao", referencedColumnName = "id")
	private Localizacao localizacao;
	
	@JsonProperty("recursos")
	@OneToMany(mappedBy = "rebelde", cascade = CascadeType.ALL)
	@JsonManagedReference
	@MapKey(name = "nome")
	private Map<String, Recurso> recursos;
	
}
