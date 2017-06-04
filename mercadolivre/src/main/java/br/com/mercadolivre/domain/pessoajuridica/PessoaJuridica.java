package br.com.mercadolivre.domain.pessoajuridica;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.mercadolivre.domain.Entity;

@javax.persistence.Entity
@javax.persistence.Table(name = "EMLPESSOAJURIDICA")
@Access(AccessType.FIELD)
@Document(indexName="mercadolivre",type="pessoafisica")
public class PessoaJuridica extends Entity implements DadosPessoaJuridica {

    private static final long serialVersionUID = -7405080292615189198L;
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cdpessoajuridica")
    private Long cdPessoaJuridica;
    @Column(name="razaosocial",length = 100, nullable = false)
    private String razaoSocial;
    @Column(length = 14, nullable = false, unique = true)
    private String cnpj;
    @Column(length = 100)
    private String endereco;

    public PessoaJuridica() {
	}
    
    public PessoaJuridica(DadosPessoaJuridica dadosPessoaJuridica) {
    	validarCNPJ(dadosPessoaJuridica.getCnpj());
    	this.cnpj = dadosPessoaJuridica.getCnpj();
    	this.endereco = dadosPessoaJuridica.getEndereco();
    	this.razaoSocial = dadosPessoaJuridica.getRazaoSocial();
    	this.cdPessoaJuridica = dadosPessoaJuridica.getCdPessoaJuridica();
	}

	@Override
    public Long getCdPessoaJuridica() {
        return cdPessoaJuridica;
    }
    
    @Override
    public String getRazaoSocial() {
        return razaoSocial;
    }
    
    @Override
    public String getCnpj() {
        return cnpj;
    }
    
    @Override
    public String getEndereco() {
        return endereco;
    }

	public void atualizarInformacoes(DadosPessoaJuridica dadosPessoaJuridica) {
		this.endereco = dadosPessoaJuridica.getEndereco();
		this.razaoSocial = dadosPessoaJuridica.getRazaoSocial();
	}

	private void validarCNPJ(String cnpj) {
		try {
			CNPJValidator cnpjValidator = new CNPJValidator(false);
			cnpjValidator.assertValid(cnpj);
		} catch (InvalidStateException  e) {
			throw new IllegalArgumentException(messageBundle.getString("msg.warn.cnpj.invalido"));
		}
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
