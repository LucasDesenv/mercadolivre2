package br.com.mercadolivreclient.redis.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import br.com.mercadolivreclient.redis.domain.PessoaFisica;
import br.com.mercadolivreclient.redis.domain.PessoaJuridica;

@Repository
public class PessoaJuridicaRepositoryImpl implements PessoaJuridicaRepository {
	private static final String KEY = "PJ";

	private RedisTemplate<String, PessoaFisica> redisTemplate;
	private HashOperations hashOps;

	@Autowired
	public PessoaJuridicaRepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	@Override
	public void savePessoaJuridica(PessoaJuridica pessoaJuridica) {
		hashOps.put(KEY+pessoaJuridica.getCnpj(), pessoaJuridica.getCnpj(), pessoaJuridica);
	}

	public PessoaJuridica findPessoaJuridica(String cnpj) {
		return (PessoaJuridica) hashOps.get(KEY+cnpj, cnpj);
	}

}
