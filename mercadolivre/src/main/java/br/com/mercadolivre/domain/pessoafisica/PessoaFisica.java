package br.com.mercadolivre.domain.pessoafisica;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.mercadolivre.domain.Entity;

/**
 * Created by lucas.souza on 02/06/2017.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "EMLPESSOAFISICA")
@Access(AccessType.FIELD)
@Document(indexName="mercadolivre",type="pessoafisica")
public class PessoaFisica extends Entity implements DadosPessoaFisica {

    private static final long serialVersionUID = -6558896975759088167L;
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cdpessoafisica")
    private Long cdPessoaFisica;
    @Column(length = 100, nullable = false)
    private String nome;
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    @Column(length = 100)
    private String endereco;
    
    public PessoaFisica() {
	}

    public PessoaFisica(DadosPessoaFisica dadosPessoaFisica) {
    	validarCPF(dadosPessoaFisica.getCpf());
    	this.nome = dadosPessoaFisica.getNome();
    	this.cpf = dadosPessoaFisica.getCpf();
    	this.endereco = dadosPessoaFisica.getEndereco();
    	this.cdPessoaFisica = dadosPessoaFisica.getCdPessoaFisica();
    }

	@Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public String getEndereco() {
        return endereco;
    }

    @Override
    public Long getCdPessoaFisica() {
        return cdPessoaFisica;
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

	public void atualizarInformacoes(DadosPessoaFisica dadosPessoaFisica) {
		this.endereco = dadosPessoaFisica.getEndereco();
		this.nome = dadosPessoaFisica.getNome();
	}

	private void validarCPF(String cpf) {
		try {
			CPFValidator cpfValidator = new CPFValidator(false);
			cpfValidator.assertValid(cpf);
		} catch (InvalidStateException  e) {
			throw new IllegalArgumentException(messageBundle.getString("msg.warn.cpf.invalido"));
		}
	}

}
