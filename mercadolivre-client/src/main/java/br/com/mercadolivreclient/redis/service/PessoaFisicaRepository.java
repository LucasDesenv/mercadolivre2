package br.com.mercadolivreclient.redis.service;

import br.com.mercadolivreclient.redis.domain.PessoaFisica;

public interface PessoaFisicaRepository {

	void savePessoaFisica(PessoaFisica pessoaFisica);

	public PessoaFisica findPessoaFisica(String cpf);
}
