package br.com.mercadolivre.dto;

import br.com.mercadolivre.domain.pessoafisica.DadosPessoaFisica;

/**
 * Created by lucas.souza on 02/06/2017.
 */
public class PessoaFisicaDTO implements DadosPessoaFisica {

    private static final long serialVersionUID = 1800443258019417469L;
    private Long cdPessoaFisica;
    private String nome;
    private String cpf;
    private String endereco;

    public PessoaFisicaDTO() {
    }

    public PessoaFisicaDTO(DadosPessoaFisica dadosPessoaFisica) {
        this.cdPessoaFisica = dadosPessoaFisica.getCdPessoaFisica();
        this.nome = dadosPessoaFisica.getNome();
        this.cpf = dadosPessoaFisica.getCpf();
        this.endereco = dadosPessoaFisica.getEndereco();
    }

    @Override
    public Long getCdPessoaFisica() {
        return cdPessoaFisica;
    }

    public void setCdPessoaFisica(Long cdPessoaFisica) {
        this.cdPessoaFisica = cdPessoaFisica;
    }

    @Override
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
        return "PessoaFisicaDTO [cdPessoaFisica=" + cdPessoaFisica + ", nome=" + nome + ", cpf=" + cpf + "]";
    }

}
