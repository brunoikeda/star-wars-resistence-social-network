package com.letscode.starwarsresistencesocialnetwork.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.letscode.starwarsresistencesocialnetwork.model.dao.Rebelde;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ContextConfiguration
public class RebeldeServiceTest {

	@Autowired
	RebeldeService service;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRetornoListaRebeldes_sucesso() {
		
		List<Rebelde> rebeldes = service.findAllRebeldes();
		
	}

}
