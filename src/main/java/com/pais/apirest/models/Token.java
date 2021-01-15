package com.pais.apirest.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_TOKEN")
public class Token implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String token;
	private String login;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expiracao;

	private boolean administrador;

	public String getToken() {
		return token;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getExpiracao() {
		return expiracao;
	}

	public void setExpiracao(Date expiracao) {
		this.expiracao = expiracao;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}
}
