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
public class RequestNegociar {
	
	@JsonProperty("rebelde1")
	private RequestRebelde rebelde1;
	
	@JsonProperty("rebelde2")
	private RequestRebelde rebelde2;
		
}
