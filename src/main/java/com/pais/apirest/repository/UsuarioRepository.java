package com.pais.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pais.apirest.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public Usuario findById(long id);

	@Query("SELECT u FROM Usuario u WHERE u.login = ?1 AND u.senha = ?2")
	public Usuario findByLoginSenha(String login, String senha);
	
	public Usuario findByLoginAndSenha(String login, String senha);

}
