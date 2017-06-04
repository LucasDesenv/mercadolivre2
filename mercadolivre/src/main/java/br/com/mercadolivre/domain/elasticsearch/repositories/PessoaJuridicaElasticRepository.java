package br.com.mercadolivre.domain.elasticsearch.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Modifying;

import br.com.mercadolivre.domain.pessoajuridica.PessoaJuridica;

public interface PessoaJuridicaElasticRepository extends ElasticsearchRepository<PessoaJuridica, Long> {
	@Modifying
	void deleteByCnpj(String cnpj);

	PessoaJuridica findByCnpj(String cnpj);

}
