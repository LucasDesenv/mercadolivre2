package br.com.mercadolivre.domain.pessoajuridica;

import java.io.Serializable;

public interface DadosPessoaJuridica extends Serializable {

    public String getRazaoSocial();

    public String getCnpj();

    public String getEndereco();

    public Long getCdPessoaJuridica();
}
