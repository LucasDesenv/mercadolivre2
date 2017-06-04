package br.com.mercadolivreclient.redis.service;

import br.com.mercadolivreclient.redis.domain.PessoaJuridica;

public interface PessoaJuridicaRepository {

	void savePessoaJuridica(PessoaJuridica pessoaFisica);

	public PessoaJuridica findPessoaJuridica(String cnpj);
}
