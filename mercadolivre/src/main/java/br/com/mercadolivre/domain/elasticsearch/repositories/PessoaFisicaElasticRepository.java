package br.com.mercadolivre.domain.elasticsearch.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Modifying;

import br.com.mercadolivre.domain.pessoafisica.PessoaFisica;

public interface PessoaFisicaElasticRepository extends ElasticsearchRepository<PessoaFisica, Long>{
	@Modifying
	void deleteByCpf(String cpf);

	PessoaFisica findByCpf(String cpf);

}
