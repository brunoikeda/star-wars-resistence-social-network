package com.letscode.starwarsresistencesocialnetwork.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.letscode.starwarsresistencesocialnetwork.config.RecursoPoints;
import com.letscode.starwarsresistencesocialnetwork.model.dao.Rebelde;
import com.letscode.starwarsresistencesocialnetwork.repository.RebeldeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RelatorioService {

	@Autowired
	RebeldeRepository rebeldeRepository;

	@Autowired
	RecursoPoints recursoPoints;

	@Value("${star-wars.max-reporte:3}")
	private Integer maxReporte;

	/***
	 * Retorna porcentagem de rebeldes traidores
	 * 
	 * @return double
	 */
	public String porcentagemTraidores() {

		List<Rebelde> rebeldes = rebeldeRepository.findAll();

		if (rebeldes.isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		} else {
			Stream<Rebelde> traidores = rebeldes.stream().filter(rebelde -> rebelde.isTraidor());
			
			double porcentTraidores = Double.valueOf(traidores.count()) / Double.valueOf(rebeldes.size()) * 100;

			String porcentTraidoresFinal = String.format("%.2f", porcentTraidores);

			return "# Porcentagem de traidores: " + porcentTraidoresFinal + "%\n";
		}

	}

	public String porcentagemRebeldes() {
		
		List<Rebelde> rebeldesTotal = rebeldeRepository.findAll();

		if (rebeldesTotal.isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		} else {
			Stream<Rebelde> rebeldes = rebeldesTotal.stream().filter(rebelde -> !rebelde.isTraidor());

			double porcentRebeldes = Double.valueOf(rebeldes.count()) / Double.valueOf(rebeldesTotal.size()) * 100;
			
			String porcentagemRebeldes = String.format("%.2f", porcentRebeldes);

			return "# Porcentagem de rebeldes: " + porcentagemRebeldes + "%\n";
		}
	}

	public String mediaRecursos() {
		List<Rebelde> rebeldesTotal = rebeldeRepository.findAll();

		if (rebeldesTotal.isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		} else {
			
			String result = "# Media de recursos por rebelde: \n";
			
			double mediaArmas = rebeldesTotal.stream()
					.filter(rebelde -> !rebelde.isTraidor())
					.mapToDouble(rebelde -> rebelde.getRecursos().get("arma").getQuantidade())
					.average()
					.getAsDouble();
			
			result = result + "   - Armas: " + mediaArmas + "\n";
			
			double mediaMunicao = rebeldesTotal.stream()
					.filter(rebelde -> !rebelde.isTraidor())
					.mapToDouble(rebelde -> rebelde.getRecursos().get("municao").getQuantidade())
					.average()
					.getAsDouble();
			result = result + "   - Municao: " + mediaMunicao + "\n";
			
			double mediaAgua = rebeldesTotal.stream()
					.filter(rebelde -> !rebelde.isTraidor())
					.mapToDouble(rebelde -> rebelde.getRecursos().get("agua").getQuantidade())
					.average()
					.getAsDouble();
			result = result + "   - Agua: " + mediaAgua + "\n";
			
			double mediaComidas = rebeldesTotal.stream()
					.filter(rebelde -> !rebelde.isTraidor())
					.mapToDouble(rebelde -> rebelde.getRecursos().get("comida").getQuantidade())
					.average()
					.getAsDouble();
			result = result + "   - Comida: " + mediaComidas + "\n";
			
			return result;
			
		}
	}

	public String pontosPerdidosPorTraidores() {
		List<Rebelde> rebeldesTotal = rebeldeRepository.findAll();

		if (rebeldesTotal.isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		} else {
			
			String result = "# Pontos perdidos por traidores: \n";
			
			double mediaArmas = rebeldesTotal.stream()
					.filter(rebelde -> rebelde.isTraidor())
					.mapToDouble(rebelde -> rebelde.getRecursos().get("arma").getQuantidade())
					.sum();
			
			result = result + "   - Armas: " + mediaArmas + "\n";
			
			double mediaMunicao = rebeldesTotal.stream()
					.filter(rebelde -> rebelde.isTraidor())
					.mapToDouble(rebelde -> rebelde.getRecursos().get("municao").getQuantidade())
					.sum();
			result = result + "   - Municao: " + mediaMunicao + "\n";
			
			double mediaAgua = rebeldesTotal.stream()
					.filter(rebelde -> rebelde.isTraidor())
					.mapToDouble(rebelde -> rebelde.getRecursos().get("agua").getQuantidade())
					.sum();
			result = result + "   - Agua: " + mediaAgua + "\n";
			
			double mediaComidas = rebeldesTotal.stream()
					.filter(rebelde -> rebelde.isTraidor())
					.mapToDouble(rebelde -> rebelde.getRecursos().get("comida").getQuantidade())
					.sum();
			result = result + "   - Comida: " + mediaComidas + "\n";
			
			result = result + "   - TOTAL: " + (mediaArmas + mediaAgua + mediaMunicao + mediaComidas);
			
			return result + "\n";
			
		}
	}

}
