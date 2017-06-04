package br.com.mercadolivre.domain.pessoafisica.service;

import br.com.mercadolivre.domain.pessoafisica.DadosPessoaFisica;

public interface PessoaFisicaService {

	DadosPessoaFisica save(DadosPessoaFisica dadosPessoaFisica);

	void delete(String cpf);

	void update(DadosPessoaFisica dadosPessoaFisica);

	DadosPessoaFisica findByCPF(String cpf);
}
