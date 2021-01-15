package com.pais.apirest.resources;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pais.apirest.models.Token;
import com.pais.apirest.models.Usuario;
import com.pais.apirest.models.UsuarioAutenticado;
import com.pais.apirest.repository.TokenRepository;
import com.pais.apirest.repository.UsuarioRepository;
import com.pais.apirest.util.Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Pais")
@CrossOrigin(origins = "*")
public class UsuarioResource {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	TokenRepository tokenRepository;

	/**
	 * Metodo de autenticacao de um usuario cadastrado.
	 * 
	 * @param login
	 * @param senha
	 * @return UsuarioAutenticado
	 * @throws Exception
	 */
	@PostMapping("/usuarios/autenticar/{login}/{senha}")
	@ApiOperation(value = "Autentica um usuario")
	public UsuarioAutenticado autenticar(@PathVariable(value = "login") String login,
			@PathVariable(value = "senha") String senha) throws Exception {

		// inicializa o objeto de retorno para nao retornar null
		UsuarioAutenticado usuarioAutenticado = new UsuarioAutenticado();
		try {
			// buscar usuario por login e senha
			Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha);

			// verifica se existe algum usuario com o login passado como parametro e se a
			// senha confere.
			if (usuario != null) {

				// cria token
				Token token = new Token();
				token.setAdministrador(usuario.isAdministrador());
				token.setLogin(usuario.getLogin());
				token.setExpiracao(Utils.getDataIncrementada5Min(new Date()));
				token.setToken(Utils.gerarHash().toString());

				// salva token
				Token tokenPersistido = tokenRepository.save(token);

				// seta os atributos do UsuarioAutenticado
				usuarioAutenticado.setToken(tokenPersistido);
				usuarioAutenticado.setUsuario(usuario);
				usuarioAutenticado.setAutenticado(true);
				usuarioAutenticado.setMsgRetorno("Usuario autenticado com sucesso!");
				return usuarioAutenticado;
			}

			usuarioAutenticado.setAutenticado(false);
			usuarioAutenticado.setMsgRetorno("Erro ao autenticar usuario!");
			return usuarioAutenticado;

		} catch (Exception e) {
			throw new Exception("Erro ao autenticar usuario.");
		}
	}

	/**
	 * Metodo que renova o token passado como parametro
	 * @param tokenStr
	 * @return Boolean
	 * @throws Exception
	 */
	@GetMapping("/usuarios/renovar-ticket/{token}")
	@ApiOperation(value = "Renova um ticket gerado anteriormente")
	public Boolean renovarTicket(@PathVariable(value = "token") String tokenStr) throws Exception {

		try {
			// buscar token pelo atributo token
			Token token = tokenRepository.findByToken(tokenStr);
			// verificar se encontrou o token, se sim, atualiza
			if (token != null && token.getToken() != null) {
				token.setExpiracao(Utils.getDataIncrementada5Min(new Date()));

				// salva token
				tokenRepository.save(token);

				return true;
			}

		} catch (Exception e) {
			throw new Exception("Erro ao renovar ticket usuario.");
		}

		return false;
	}

}
