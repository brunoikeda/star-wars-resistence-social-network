package com.letscode.starwarsresistencesocialnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class StarWarsResistenceSocialNetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarWarsResistenceSocialNetworkApplication.class, args);
	}

}
