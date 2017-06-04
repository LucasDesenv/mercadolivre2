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

import br.com.mercadolivre.domain.pessoafisica.DadosPessoaFisica;
import br.com.mercadolivre.domain.pessoafisica.service.PessoaFisicaService;
import br.com.mercadolivre.dto.PessoaFisicaDTO;

/**
 * Created by lucas.souza on 02/06/2017.
 */
@RestController
@RequestMapping(value = "/v1/api/pessoa/fisica", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class PessoaFisicaController {

	@Autowired
	private PessoaFisicaService pessoaFisicaService;

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> savePessoaFisica(@RequestBody PessoaFisicaDTO pessoaFisicaDTO) {
		DadosPessoaFisica pessoaSalva = pessoaFisicaService.save(pessoaFisicaDTO);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/v1/api/pessoa/fisica/" + pessoaSalva.getCpf());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<PessoaFisicaDTO> findPessoaFisica(@PathVariable("cpf") String cpf) {
		final DadosPessoaFisica dadosPessoaFisica = pessoaFisicaService.findByCPF(cpf);
		if (dadosPessoaFisica == null)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		final PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(dadosPessoaFisica);
		return new ResponseEntity<>(pessoaFisicaDTO, HttpStatus.OK);
	}

	@RequestMapping(value = "/{cpf}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletePessoaFisica(@PathVariable("cpf") String cpf) {
		pessoaFisicaService.delete(cpf);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<String> updatePessoaFisica(@RequestBody PessoaFisicaDTO pessoaFisicaDTO) {
		pessoaFisicaService.update(pessoaFisicaDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
