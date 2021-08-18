package com.letscode.starwarsresistencesocialnetwork.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.letscode.starwarsresistencesocialnetwork.model.dao.Rebelde;

@Repository
public interface RebeldeRepository extends JpaRepository<Rebelde, Long>{

}
