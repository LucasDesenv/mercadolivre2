package br.com.mercadolivre.domain.pessoajuridica.service;

import br.com.mercadolivre.domain.pessoajuridica.DadosPessoaJuridica;

public interface PessoaJuridicaService {

	DadosPessoaJuridica save(DadosPessoaJuridica dadosPessoaJuridica);

	void delete(String cnpj);

	void update(DadosPessoaJuridica dadosPessoaJuridica);
	
	DadosPessoaJuridica findByCNPJ(String cpf);
}
