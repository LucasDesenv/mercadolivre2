package br.com.mercadolivre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivre.domain.pessoajuridica.DadosPessoaJuridica;
import br.com.mercadolivre.domain.pessoajuridica.service.PessoaJuridicaService;
import br.com.mercadolivre.dto.PessoaJuridicaDTO;

/**
 * Created by lucas.souza on 02/06/2017.
 */
@RestController
@RequestMapping(value = "/v1/api/pessoa/juridica", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class PessoaJuridicaController {

	@Autowired
	private PessoaJuridicaService pessoaService;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> savePessoaJuridica(@RequestBody PessoaJuridicaDTO pessoaJuridicaDTO) {
		DadosPessoaJuridica pessoaSalva = pessoaService.save(pessoaJuridicaDTO);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/v1/api/pessoa/juridica/" + pessoaSalva.getCnpj());
		return new ResponseEntity<>(headers,HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{cnpj}", method = RequestMethod.GET)
	public ResponseEntity<PessoaJuridicaDTO> findPessoaJuridica(@PathVariable("cnpj") String cnpj) {
		final DadosPessoaJuridica dadosPessoaJuridica = pessoaService.findByCNPJ(cnpj);
		if (dadosPessoaJuridica == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		final PessoaJuridicaDTO pessoaJuridicaDTO = new PessoaJuridicaDTO(dadosPessoaJuridica);
		return new ResponseEntity<>(pessoaJuridicaDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{cnpj}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletePessoaJuridica(@PathVariable("cnpj") String cnpj) {
		pessoaService.delete(cnpj);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<String> updatePessoaJuridica(@RequestBody PessoaJuridicaDTO pessoaJuridicaDTO) {
		pessoaService.update(pessoaJuridicaDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
