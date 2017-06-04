package br.com.mercadolivreclient.redis.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import br.com.mercadolivreclient.redis.domain.PessoaFisica;

@Repository
public class PessoaFisicaRepositoryImpl implements PessoaFisicaRepository {
	private static final String KEY = "PF";

	private RedisTemplate<String, PessoaFisica> redisTemplate;
	private HashOperations hashOps;

	@Autowired
	public PessoaFisicaRepositoryImpl(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOps = redisTemplate.opsForHash();
	}

	@Override
	public void savePessoaFisica(PessoaFisica pessoaFisica) {
		hashOps.put(KEY + pessoaFisica.getCpf(), pessoaFisica.getCpf(), pessoaFisica);
	}

	public PessoaFisica findPessoaFisica(String cpf) {
		return (PessoaFisica) hashOps.get(KEY + cpf, cpf);
	}

}
