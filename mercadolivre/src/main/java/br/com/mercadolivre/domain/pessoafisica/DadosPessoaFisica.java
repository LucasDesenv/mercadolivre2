package br.com.mercadolivre.domain.pessoafisica;

import java.io.Serializable;

public interface DadosPessoaFisica extends Serializable {

    public String getNome();

    public String getCpf();

    public String getEndereco();

    public Long getCdPessoaFisica();
}
