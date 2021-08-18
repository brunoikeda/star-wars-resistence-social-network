package com.letscode.starwarsresistencesocialnetwork.model.dao;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Negociacao {
	
	@JsonProperty("rebelde1")
	private Rebelde rebelde1;
	
	@JsonProperty("rebelde2")
	private Rebelde rebelde2;

}
