-- MySql
CREATE SCHEMA `mercadolivre`;
CREATE TABLE `mercadolivre`.`emlpessoafisica` (
  `cdPessoaFisica` BIGINT(20) NOT NULL auto_increment,
  `nome` VARCHAR(100) NOT NULL,
  `cpf` VARCHAR(11) NOT NULL,
  `endereco` VARCHAR(100) NULL,
  PRIMARY KEY (`cdPessoaFisica`),
  UNIQUE INDEX `cpf_UNIQUE` (`cpf` ASC));

  
CREATE TABLE `mercadolivre`.`emlpessoajuridica` (
  `cdPessoaJuridica` BIGINT(20) NOT NULL auto_increment,
  `razaoSocial` VARCHAR(100) NOT NULL,
  `cnpj` VARCHAR(14) NOT NULL,
  `endereco` VARCHAR(100) NULL,
  PRIMARY KEY (`cdPessoaJuridica`),
  UNIQUE INDEX `cnpj_UNIQUE` (`cnpj` ASC));
  