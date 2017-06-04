package br.com.mercadolivre;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import br.com.mercadolivre.domain.pessoajuridica.PessoaJuridica;
import br.com.mercadolivre.dto.PessoaJuridicaDTO;
@Sql(scripts = { "/script/shutdown.sql" }, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class PessoaJuridicaInteTest extends SpringTest {
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		createElasticSearchIndex(PessoaJuridica.class);
	}

	@After
	public void end(){
		destroyElasticSearchIndex(PessoaJuridica.class);
	}
	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
		Assert.assertNotNull("the JSON message converter must not be null", mappingJackson2HttpMessageConverter);
	}

	@Test
	@Rollback
	public void deveRetornarNoContentAoBuscarPessoaJuridicaPorCnpjInexistente() {
		configureUrl("/v1/api/pessoa/juridica/{cnpj}");
		ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(url, String.class, "129384");
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}
	
	@Test
	@Rollback
	public void deveRetornarCREATEDAoInserirPessoaJuridica() throws Exception {
		inserirPessoaJuridica();
	}

	@Test
	@Rollback
	public void deveRetornarOKAoBuscarPessoaJuridicaExistente() throws Exception {
		inserirPessoaJuridica();
		configureUrl("/v1/api/pessoa/juridica/{cnpj}");
		ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(url, String.class, "79642858000100");
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	@Rollback
	public void deveRetornarBADREQUESTAoInserirComMesmoCNPJ() throws Exception {
		inserirPessoaJuridica();
		PessoaJuridicaDTO pessoaJuridicaDTO = new PessoaJuridicaDTO();
		pessoaJuridicaDTO.setCnpj("79642858000100");
		pessoaJuridicaDTO.setRazaoSocial("Kleber");
		pessoaJuridicaDTO.setEndereco("Buenos Aires");
		String json = toJson(pessoaJuridicaDTO);
		MvcResult mvcResult = this.mockMvc.perform(post("/v1/api/pessoa/juridica").contentType(contentType).content(json))
				.andExpect(status().isBadRequest()).andReturn();
		Assertions.assertThat(mvcResult.getResponse().getContentAsString())
				.contains(messageBundle.getString("msg.warn.cnpj.ja.cadastrado"));
		this.mockMvc.perform(get("/v1/api/pessoa/juridica/{cnpj}", "79642858000100")).andExpect(status().isOk());
	}
	

	@Test
	@Rollback
	public void deveRetornarBADREQUESTAoInserirComCNPJInvalido() throws Exception {
		PessoaJuridicaDTO pessoaJuridicaDTO = new PessoaJuridicaDTO();
		pessoaJuridicaDTO.setCnpj("79642858000109");
		pessoaJuridicaDTO.setRazaoSocial("Alonso");
		pessoaJuridicaDTO.setEndereco("FLoripa");
		String json = toJson(pessoaJuridicaDTO);
		MvcResult mvcResult = this.mockMvc.perform(post("/v1/api/pessoa/juridica").contentType(contentType).content(json))
				.andExpect(status().isBadRequest()).andReturn();
		Assertions.assertThat(mvcResult.getResponse().getContentAsString())
				.contains(messageBundle.getString("msg.warn.cnpj.invalido"));
	}
	
	@Test
	@Rollback
	public void devePoderDeletarPessoa() throws Exception {
		inserirPessoaJuridica();
		this.mockMvc.perform(delete("/v1/api/pessoa/juridica/{cnpj}","79642858000100")).andExpect(status().isOk());
	}
	

	@Test
	@Rollback
	public void devePoderAtualizarDadosCadastrais() throws Exception {
		inserirPessoaJuridica();
		PessoaJuridicaDTO pessoaJuridicaDTO = new PessoaJuridicaDTO();
		pessoaJuridicaDTO.setCnpj("79642858000100");
		pessoaJuridicaDTO.setRazaoSocial("José ATUALIZADO");
		pessoaJuridicaDTO.setEndereco("São Paulo");
		this.mockMvc.perform(put("/v1/api/pessoa/juridica").contentType(contentType).content(toJson(pessoaJuridicaDTO)))
				.andExpect(status().isOk());
		PessoaJuridicaDTO pessoaFisicaAtualizada = findByCNPJ();
		Assertions.assertThat(pessoaFisicaAtualizada.getRazaoSocial()).isEqualTo(pessoaJuridicaDTO.getRazaoSocial());
		Assertions.assertThat(pessoaFisicaAtualizada.getEndereco()).isEqualTo(pessoaJuridicaDTO.getEndereco());
	}
	
	@Test
	@Rollback
	public void naoDevePoderAtualizarCNPJ() throws Exception {
		inserirPessoaJuridica();
		PessoaJuridicaDTO pessoaJuridicaDTO = findByCNPJ();
		pessoaJuridicaDTO.setCnpj("49642858000100");
		pessoaJuridicaDTO.setRazaoSocial("José Novo");
		pessoaJuridicaDTO.setEndereco("Rio de Janeiro");
		MvcResult mvcResult = this.mockMvc.perform(put("/v1/api/pessoa/juridica").contentType(contentType).content(toJson(pessoaJuridicaDTO)))
				.andExpect(status().isBadRequest()).andReturn();
		Assertions.assertThat(mvcResult.getResponse().getContentAsString()).contains(messageBundle.getString("msg.warn.pessoa.nao.encontrada"));
	}

	
	private PessoaJuridicaDTO findByCNPJ() throws Exception, UnsupportedEncodingException {
		MvcResult mvcResult = this.mockMvc.perform(get("/v1/api/pessoa/juridica/{cnpj}", "79642858000100")).andExpect(status().isOk()).andReturn();
		PessoaJuridicaDTO pessoaJuridicaAtualizada = new Gson().fromJson(mvcResult.getResponse().getContentAsString(), PessoaJuridicaDTO.class);
		return pessoaJuridicaAtualizada;
	}

	
	private void inserirPessoaJuridica() throws IOException, Exception {
		PessoaJuridicaDTO pessoaJuridicaDTO = new PessoaJuridicaDTO();
		pessoaJuridicaDTO.setCnpj("79642858000100");
		pessoaJuridicaDTO.setRazaoSocial("Afonso");
		pessoaJuridicaDTO.setEndereco("FLoripa");
		String json = toJson(pessoaJuridicaDTO);
		this.mockMvc.perform(post("/v1/api/pessoa/juridica").contentType(contentType).content(json))
				.andExpect(status().isCreated());
	}
}


