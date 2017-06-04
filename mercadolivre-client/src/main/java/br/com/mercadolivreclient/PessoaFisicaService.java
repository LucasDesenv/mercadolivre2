package br.com.mercadolivreclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.mercadolivreclient.redis.domain.PessoaFisica;
import br.com.mercadolivreclient.redis.domain.PessoaJuridica;
import br.com.mercadolivreclient.redis.service.PessoaFisicaRepository;
import br.com.mercadolivreclient.redis.service.PessoaJuridicaRepository;

@Service
public class PessoaFisicaService {

	private final RestTemplate restTemplate;

	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;

	public PessoaFisicaService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@HystrixCommand(fallbackMethod = "fallbackFindByCpf")
	public PessoaFisica findPessoaFisicaByCPF(String cpf) {
		String forObject = restTemplate.getForObject("http://localhost:8080/ml/v1/api/pessoa/fisica/" + cpf,
				String.class);
		if (forObject == null)
			return null;
		final PessoaFisica pessoaFisica = new Gson().fromJson(forObject, PessoaFisica.class);
		pessoaFisicaRepository.savePessoaFisica(pessoaFisica);
		return pessoaFisica;
	}

	public PessoaFisica fallbackFindByCpf(String cpf) {
		return pessoaFisicaRepository.findPessoaFisica(cpf);
	}

	@HystrixCommand(fallbackMethod = "fallbackFindByCNPJ")
	public PessoaJuridica findPessoaJuridicaByCNPJ(String cnpj) {
		String forObject = restTemplate.getForObject("http://localhost:8080/ml/v1/api/pessoa/juridica/" + cnpj,
				String.class);
		if (forObject == null)
			return null;
		final PessoaJuridica pessoaJuridica = new Gson().fromJson(forObject, PessoaJuridica.class);
		pessoaJuridicaRepository.savePessoaJuridica(pessoaJuridica);
		return pessoaJuridica;
	}

	public PessoaJuridica fallbackFindByCNPJ(String cnpj) {
		return pessoaJuridicaRepository.findPessoaJuridica(cnpj);
	}
}
