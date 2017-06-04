package br.com.mercadolivre.domain.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import br.com.mercadolivre.domain.pessoafisica.DadosPessoaFisica;
import br.com.mercadolivre.domain.pessoafisica.PessoaFisica;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

	DadosPessoaFisica findByCpf(String cpf);
	@Modifying
	void deleteByCpf(String cpf);

}
