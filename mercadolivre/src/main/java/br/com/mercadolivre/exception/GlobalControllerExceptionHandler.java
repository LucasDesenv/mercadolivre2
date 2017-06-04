package br.com.mercadolivre.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.mercadolivre.domain.pessoajuridica.service.PessoaJuridicaServiceImpl;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	private static final Log LOG = LogFactory.getLog(PessoaJuridicaServiceImpl.class);

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DataAccessException.class)
	public @ResponseBody ExceptionInfo dataAccessException(Exception e) {
		LOG.error(e.getMessage(), e);
		return new ExceptionInfo(e.getMessage(), "Erro Desconhecido");
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ RuntimeException.class})
	public @ResponseBody ExceptionInfo runtimeException(Exception e) {
		LOG.error(e.getMessage(), e);
		return new ExceptionInfo(e.getMessage(), "Erro Desconhecido");
	}

	@ExceptionHandler(ServiceWarningException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody ExceptionInfo serviceWarningException(ServiceWarningException e) {
		LOG.warn(e.getMessage(), e);
		return new ExceptionInfo(e.getMessage());
	}
}
