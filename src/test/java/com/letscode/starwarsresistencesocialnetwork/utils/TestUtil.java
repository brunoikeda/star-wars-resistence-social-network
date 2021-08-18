package com.letscode.starwarsresistencesocialnetwork.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestUtil {

	public String readFileResponse(String filePath) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line);
			}
		} catch (IOException e) {
			log.error("Fail to read file " + e.getMessage());
		}
		return resultStringBuilder.toString();
	}
	
}
