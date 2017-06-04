package br.com.mercadolivre.domain.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import br.com.mercadolivre.domain.pessoajuridica.DadosPessoaJuridica;
import br.com.mercadolivre.domain.pessoajuridica.PessoaJuridica;

public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {

	DadosPessoaJuridica findByCnpj(String cnpj);
	@Modifying
	void deleteByCnpj(String cnpj);

}
