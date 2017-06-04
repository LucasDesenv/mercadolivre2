package br.com.mercadolivre.domain;

import java.io.Serializable;

import javax.persistence.Transient;

import br.com.mercadolivre.util.MessageBundle;

public abstract class Entity implements Serializable {

	private static final long serialVersionUID = 2236616514044960490L;
	protected Entity(){}
	@Transient
	@org.springframework.data.annotation.Transient
	protected transient MessageBundle messageBundle = MessageBundle.getInstance();

}
