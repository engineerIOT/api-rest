package com.pais.apirest.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pais.apirest.models.Pais;
import com.pais.apirest.models.Token;
import com.pais.apirest.repository.PaisRepository;
import com.pais.apirest.repository.TokenRepository;
import com.pais.apirest.util.Utils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Pais")
@CrossOrigin(origins = "*")
public class PaisResource {

	@Autowired
	PaisRepository paisRepository;

	@Autowired
	TokenRepository tokenRepository;

	/**
	 * Metodo que retorna uma lista de paises
	 * 
	 * @param tokenStr
	 * @return List<Pais>
	 * @throws Exception
	 */
	@GetMapping("/pais/listar/{token}")
	@ApiOperation(value = "Retorna uma lista de paises")
	public List<Pais> listar(@PathVariable(value = "token") String tokenStr) throws Exception {
		List<Pais> listPaises = new ArrayList<>();

		try {
			// busca token
			Token token = tokenRepository.findByToken(tokenStr);

			// caso exista um token
			if (token != null) {
				// verificar se o token nao esta expirado
				Date dataAtual = new Date();
				boolean tokenExpirado = dataAtual.after(Utils.getDataIncrementada5Min(token.getExpiracao()));

				// se token valido, listar paises
				if (!tokenExpirado) {
					listPaises = paisRepository.findAll();
				}
			}
		} catch (Exception e) {
			throw new Exception("Erro ao listar paises.");
		}
		return listPaises;
	}

	/**
	 * Metodo que persiste um pais no banco de dados
	 * 
	 * @param pais
	 * @param tokenStr
	 * @return Pais
	 * @throws Exception
	 */
	@PostMapping("/pais/salvar/{token}")
	@ApiOperation(value = "Salva um pais")
	public Pais salvar(@RequestBody Pais pais, @PathVariable(value = "token") String tokenStr) throws Exception {

		try {
			// busca token
			Token token = tokenRepository.findByToken(tokenStr);

			// caso exista um token
			if (token != null) {
				// verificar se o token nao esta expirado
				Date dataAtual = new Date();
				boolean tokenExpirado = dataAtual.after(Utils.getDataIncrementada5Min(token.getExpiracao()));

				// se token valido, salvar pais
				if (!tokenExpirado) {
					return paisRepository.save(pais);
				}
			}
		} catch (Exception e) {
			throw new Exception("Erro ao listar paises.");
		}

		return new Pais();
	}

	/**
	 * Metodo que pesquisa um pais de acordo com o nome passado como parametro
	 * 
	 * @param nome
	 * @param tokenStr
	 * @return List<Pais
	 * @throws Exception
	 */
	@GetMapping("/pais/pesquisar/{nome}/{token}")
	@ApiOperation(value = "Retorna uma lista de paises")
	public List<Pais> pesquisar(@PathVariable(value = "nome") String nome,
			@PathVariable(value = "token") String tokenStr) throws Exception {
		List<Pais> listPaises = new ArrayList<>();

		try {
			// busca token
			Token token = tokenRepository.findByToken(tokenStr);
			// caso exista um token
			if (token != null) {
				// verificar se o token nao esta expirado
				Date dataAtual = new Date();
				boolean tokenExpirado = dataAtual.after(Utils.getDataIncrementada5Min(token.getExpiracao()));
				// se token valido, listar paises
				if (!tokenExpirado) {
					listPaises = paisRepository.findByNomeIgnoreCaseContaining(nome);
				}

			}

		} catch (Exception e) {
			throw new Exception("Erro ao pesquisar paises.");
		}

		return listPaises;

	}

	/**
	 * Metodo que exclui um pais de acordo com o id passado como parametro
	 * 
	 * @param id
	 * @param tokenStr
	 * @return boolean
	 * @throws Exception
	 */
	@DeleteMapping("/pais/excluir/{id}/{token}")
	@ApiOperation(value = "Deleta um pais")
	public boolean excluir(@PathVariable(value = "id") long id, @PathVariable(value = "token") String tokenStr)
			throws Exception {
		Pais pais;
		try {
			// busca token
			Token token = tokenRepository.findByToken(tokenStr);
			// caso exista um token
			if (token != null) {
				// verificar se o token nao esta expirado
				Date dataAtual = new Date();
				boolean tokenExpirado = dataAtual.after(Utils.getDataIncrementada5Min(token.getExpiracao()));
				// se token valido, listar paises
				if (!tokenExpirado) {
					pais = paisRepository.findById(id);
					if (pais != null) {
						paisRepository.delete(pais);
						return true;
					}
				}
			}

		} catch (Exception e) {
			throw new Exception("Erro ao excluir paises.");
		}

		return false;

	}

}
