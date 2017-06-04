package br.com.mercadolivreclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mercadolivreclient.redis.domain.PessoaFisica;
import br.com.mercadolivreclient.redis.domain.PessoaJuridica;

@RestController
public class ClientController {
	@Autowired
	private PessoaFisicaService pessoaService;

	@RequestMapping("/pessoa-fisica/{cpf}")
	public PessoaFisica findPessoaFisicaByCPF(@PathVariable String cpf) {
		return pessoaService.findPessoaFisicaByCPF(cpf);
	}

	@RequestMapping("/pessoa-juridica/{cnpj}")
	public PessoaJuridica findPessoaJuridicaByCNPJ(@PathVariable String cnpj) {
		return pessoaService.findPessoaJuridicaByCNPJ(cnpj);
	}

}
