package com.letscode.starwarsresistencesocialnetwork.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.letscode.starwarsresistencesocialnetwork.model.dao.Recurso;

import lombok.Getter;
import lombok.Setter;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "star-wars")
@Getter
@Setter
public class RecursoPoints implements InitializingBean{

    private List<Recurso> recursos;
    
	Map<String, Recurso> recursoMap = new HashMap<>();


	@Override
	public void afterPropertiesSet() throws Exception {
		for (Recurso recurso : recursos) {
			recursoMap.put(recurso.getNome(), recurso);
		}
	}
	
	public Long getPoints(String nome) {
		return recursoMap.get(nome.toLowerCase()).getPontos();
	}
	
}
