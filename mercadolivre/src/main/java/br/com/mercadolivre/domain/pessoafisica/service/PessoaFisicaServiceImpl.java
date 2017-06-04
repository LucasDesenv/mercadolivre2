package br.com.mercadolivre.domain.pessoafisica.service;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.mercadolivre.domain.elasticsearch.repositories.PessoaFisicaElasticRepository;
import br.com.mercadolivre.domain.jpa.repositories.PessoaFisicaRepository;
import br.com.mercadolivre.domain.pessoafisica.DadosPessoaFisica;
import br.com.mercadolivre.domain.pessoafisica.PessoaFisica;
import br.com.mercadolivre.exception.ServiceWarningException;
import br.com.mercadolivre.util.MessageBundle;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class,
		ServiceWarningException.class, Exception.class })
public class PessoaFisicaServiceImpl implements PessoaFisicaService {

	private static final Log LOG = LogFactory.getLog(PessoaFisicaServiceImpl.class);
	@Autowired
	private PessoaFisicaRepository pessoaFisicaRepository;
	@Autowired
	private PessoaFisicaElasticRepository pessoaFisicaElasticRepository;
	@Autowired
	private MessageBundle messageBundle;

	@Override
	@Transactional(readOnly = false)
	public DadosPessoaFisica save(DadosPessoaFisica dadosPessoaFisica) {
		try {
			PessoaFisica pessoaFisica = new PessoaFisica(dadosPessoaFisica);
			Validate.isTrue(findByCPF(pessoaFisica.getCpf()) == null,
					messageBundle.getString("msg.warn.cpf.ja.cadastrado"));
			pessoaFisicaRepository.save(pessoaFisica);
			return pessoaFisicaElasticRepository.save(pessoaFisica);
		} catch (IllegalArgumentException e) {
			LOG.warn(e.getMessage(), e);
			throw new ServiceWarningException(e.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(String cpf) {
		try {
			pessoaFisicaRepository.deleteByCpf(cpf);
			pessoaFisicaElasticRepository.deleteByCpf(cpf);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void update(DadosPessoaFisica dadosPessoaFisica) {
		try {
			final DadosPessoaFisica dadosPessoa = findByCPF(dadosPessoaFisica.getCpf());
			Validate.isTrue(dadosPessoa != null, messageBundle.getString("msg.warn.pessoa.nao.encontrada"));
			final PessoaFisica pessoaFisica = new PessoaFisica(dadosPessoa);
			pessoaFisica.atualizarInformacoes(dadosPessoaFisica);
			pessoaFisicaRepository.save(pessoaFisica);
			pessoaFisicaElasticRepository.save(pessoaFisica);
		} catch (IllegalArgumentException e) {
			LOG.warn(e.getMessage(), e);
			throw new ServiceWarningException(e.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public DadosPessoaFisica findByCPF(String cpf) {
		return pessoaFisicaElasticRepository.findByCpf(cpf);
	}

}
