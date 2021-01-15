package com.pais.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pais.apirest.models.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

	public Token findById(long id);

	@Query("SELECT t FROM Token t WHERE t.token LIKE ?1")
	public Token findByTokenQry(String tokenStr);
	
	public Token findByToken(String token);

}

