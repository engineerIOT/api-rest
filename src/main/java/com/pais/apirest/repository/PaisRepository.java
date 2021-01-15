package com.pais.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pais.apirest.models.Pais;

public interface PaisRepository extends JpaRepository<Pais, Long> {

	public Pais findById(long id);

	public List<Pais> findByNomeIgnoreCaseContaining(String nome);
}
