package br.com.mercadolivre.dto;

import br.com.mercadolivre.domain.pessoajuridica.DadosPessoaJuridica;

/**
 * Created by lucas.souza on 02/06/2017.
 */
public class PessoaJuridicaDTO implements DadosPessoaJuridica {

    private static final long serialVersionUID = 1800443258019417469L;
    private Long cdPessoaJuridica;
    private String razaoSocial;
    private String cnpj;
    private String endereco;

    public PessoaJuridicaDTO() {
    }

    public PessoaJuridicaDTO(DadosPessoaJuridica pessoaJuridica) {
        this.cdPessoaJuridica = pessoaJuridica.getCdPessoaJuridica();
        this.razaoSocial = pessoaJuridica.getRazaoSocial();
        this.endereco = pessoaJuridica.getEndereco();
        this.cnpj = pessoaJuridica.getCnpj();
    }

    @Override
    public Long getCdPessoaJuridica() {
        return cdPessoaJuridica;
    }

    public void setCdPessoaJuridica(Long cdPessoaJuridica) {
        this.cdPessoaJuridica = cdPessoaJuridica;
    }

    @Override
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @Override
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "PessoaJuridicaDTO [cdPessoaJuridica=" + cdPessoaJuridica + ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + "]";
    }


}
