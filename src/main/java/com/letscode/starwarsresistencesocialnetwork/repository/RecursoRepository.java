package com.letscode.starwarsresistencesocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.letscode.starwarsresistencesocialnetwork.model.dao.Recurso;

@Repository
public interface RecursoRepository extends JpaRepository<Recurso, Long>{

}
