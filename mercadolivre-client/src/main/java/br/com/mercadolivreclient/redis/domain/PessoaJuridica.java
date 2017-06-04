package br.com.mercadolivreclient.redis.domain;

import java.io.Serializable;

public class PessoaJuridica implements Serializable {
	private static final long serialVersionUID = 6730732013181151783L;
	private Long cdPessoaJuridica;
	private String razaoSocial;
	private String cnpj;
	private String endereco;

	public PessoaJuridica() {
	}

	public Long getCdPessoaJuridica() {
		return cdPessoaJuridica;
	}

	public void setCdPessoaJuridica(Long cdPessoaJuridica) {
		this.cdPessoaJuridica = cdPessoaJuridica;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cdPessoaJuridica == null) ? 0 : cdPessoaJuridica.hashCode());
		result = prime * result + ((cnpj == null) ? 0 : cnpj.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PessoaJuridica other = (PessoaJuridica) obj;
		if (cdPessoaJuridica == null) {
			if (other.cdPessoaJuridica != null)
				return false;
		} else if (!cdPessoaJuridica.equals(other.cdPessoaJuridica))
			return false;
		if (cnpj == null) {
			if (other.cnpj != null)
				return false;
		} else if (!cnpj.equals(other.cnpj))
			return false;
		return true;
	}

}