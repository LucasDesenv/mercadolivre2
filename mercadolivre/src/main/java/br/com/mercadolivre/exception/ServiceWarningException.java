package br.com.mercadolivre.exception;

public class ServiceWarningException extends RuntimeException {

	public ServiceWarningException(String msg) {
		super(msg);
	}

	private static final long serialVersionUID = 1244960152579608940L;

}
