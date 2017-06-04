package br.com.mercadolivre.domain.pessoajuridica.service;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.mercadolivre.domain.elasticsearch.repositories.PessoaJuridicaElasticRepository;
import br.com.mercadolivre.domain.jpa.repositories.PessoaJuridicaRepository;
import br.com.mercadolivre.domain.pessoajuridica.DadosPessoaJuridica;
import br.com.mercadolivre.domain.pessoajuridica.PessoaJuridica;
import br.com.mercadolivre.exception.ServiceWarningException;
import br.com.mercadolivre.util.MessageBundle;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class,
		ServiceWarningException.class, Exception.class })
public class PessoaJuridicaServiceImpl implements PessoaJuridicaService {

	private static final Log LOG = LogFactory.getLog(PessoaJuridicaServiceImpl.class);
	@Autowired
	private PessoaJuridicaRepository pessoaJuridicaRepository;
	@Autowired
	private PessoaJuridicaElasticRepository pessoaJuridicaElasticRepository;
	@Autowired
	private MessageBundle messageBundle;

	@Override
	@Transactional(readOnly = false)
	public DadosPessoaJuridica save(DadosPessoaJuridica dadosPessoaJuridica) {
		try {
			PessoaJuridica pessoaJuridica = new PessoaJuridica(dadosPessoaJuridica);
			Validate.isTrue(findByCNPJ(pessoaJuridica.getCnpj()) == null,
					messageBundle.getString("msg.warn.cnpj.ja.cadastrado"));
			pessoaJuridicaRepository.save(pessoaJuridica);
			return pessoaJuridicaElasticRepository.save(pessoaJuridica);
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
	public void delete(String cnpj) {
		pessoaJuridicaRepository.deleteByCnpj(cnpj);
		pessoaJuridicaElasticRepository.deleteByCnpj(cnpj);
	}

	@Override
	@Transactional(readOnly = false)
	public void update(DadosPessoaJuridica dadosPessoaJuridicaParaAtualizar) {
		try {
			final DadosPessoaJuridica dadosPessoa = findByCNPJ(dadosPessoaJuridicaParaAtualizar.getCnpj());
			Validate.isTrue(dadosPessoa != null, messageBundle.getString("msg.warn.pessoa.nao.encontrada"));

			final PessoaJuridica pessoaJuridica = new PessoaJuridica(dadosPessoa);
			pessoaJuridica.atualizarInformacoes(dadosPessoaJuridicaParaAtualizar);
			pessoaJuridicaRepository.save(pessoaJuridica);
			pessoaJuridicaElasticRepository.save(pessoaJuridica);
		} catch (IllegalArgumentException e) {
			LOG.warn(e.getMessage(), e);
			throw new ServiceWarningException(e.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public DadosPessoaJuridica findByCNPJ(String cnpj) {
		return pessoaJuridicaElasticRepository.findByCnpj(cnpj);
	}

}
