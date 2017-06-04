package br.com.mercadolivreclient.redis.domain;

import java.io.Serializable;

public class PessoaFisica implements Serializable{
	private static final long serialVersionUID = -8018976350534733893L;
	private Long cdPessoaFisica;
	private String nome;
	private String cpf;
	private String endereco;

	public PessoaFisica() {
	}

	public Long getCdPessoaFisica() {
		return cdPessoaFisica;
	}

	public void setCdPessoaFisica(Long cdPessoaFisica) {
		this.cdPessoaFisica = cdPessoaFisica;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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
		result = prime * result + (cdPessoaFisica == null ? 0 : cdPessoaFisica.hashCode());
		result = prime * result + (cpf == null ? 0 : cpf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof PessoaFisica)) {
			return false;
		}
		PessoaFisica other = (PessoaFisica) obj;
		if (cdPessoaFisica == null) {
			if (other.cdPessoaFisica != null) {
				return false;
			}
		} else if (!cdPessoaFisica.equals(other.cdPessoaFisica)) {
			return false;
		}
		if (cpf == null) {
			if (other.cpf != null) {
				return false;
			}
		} else if (!cpf.equals(other.cpf)) {
			return false;
		}
		return true;
	}

}
