package com.pais.apirest.models;

import java.io.Serializable;

public class UsuarioAutenticado implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private Token token;
	private boolean autenticado;
	private String msgRetorno;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}

	public String getMsgRetorno() {
		return msgRetorno;
	}

	public void setMsgRetorno(String msgRetorno) {
		this.msgRetorno = msgRetorno;
	}

}
