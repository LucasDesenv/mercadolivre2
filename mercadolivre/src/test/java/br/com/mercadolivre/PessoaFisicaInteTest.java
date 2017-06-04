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

import br.com.mercadolivre.domain.pessoafisica.PessoaFisica;
import br.com.mercadolivre.dto.PessoaFisicaDTO;

@Sql(scripts = { "/script/shutdown.sql" }, executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class PessoaFisicaInteTest extends SpringTest {
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		createElasticSearchIndex(PessoaFisica.class);
	}

	@After
	public void end(){
		destroyElasticSearchIndex(PessoaFisica.class);
	}

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
		Assert.assertNotNull("the JSON message converter must not be null", mappingJackson2HttpMessageConverter);
	}

	@Test
	@Rollback
	public void deveRetornarNoContentAoBuscarPessoaFisicaPorCpfInexistente() {
		configureUrl("/v1/api/pessoa/fisica/{cpf}");
		ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(url, String.class, "129384");
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.NO_CONTENT.value());
	}

	@Test
	@Rollback
	public void deveRetornarCREATEDAoInserirPessoaFisica() throws Exception {
		PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO();
		pessoaFisicaDTO.setCpf("05810192530");
		pessoaFisicaDTO.setNome("Afonso");
		pessoaFisicaDTO.setEndereco("FLoripa");
		String json = toJson(pessoaFisicaDTO);
		this.mockMvc.perform(post("/v1/api/pessoa/fisica").contentType(contentType).content(json))
				.andExpect(status().isCreated());
	}

	@Test
	@Rollback
	public void deveRetornarOKAoBuscarPessoaFisicaExistente() throws Exception {
		inserirPessoaFisica();
		configureUrl("/v1/api/pessoa/fisica/{cpf}");
		ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(url, String.class, "05810192530");
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	@Rollback
	public void deveRetornarBADREQUESTAoInserirComMesmoCPF() throws Exception {
		inserirPessoaFisica();
		PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO();
		pessoaFisicaDTO.setCpf("05810192530");
		pessoaFisicaDTO.setNome("Alonso");
		pessoaFisicaDTO.setEndereco("FLoripa");
		String json = toJson(pessoaFisicaDTO);
		MvcResult mvcResult = this.mockMvc.perform(post("/v1/api/pessoa/fisica").contentType(contentType).content(json))
				.andExpect(status().isBadRequest()).andReturn();
		Assertions.assertThat(mvcResult.getResponse().getContentAsString())
				.contains(messageBundle.getString("msg.warn.cpf.ja.cadastrado"));
		this.mockMvc.perform(get("/v1/api/pessoa/fisica/{cpf}", "05810192530")).andExpect(status().isOk());
	}

	@Test
	@Rollback
	public void deveRetornarBADREQUESTAoInserirComCPFInvalido() throws Exception {
		PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO();
		pessoaFisicaDTO.setCpf("12345678900");
		pessoaFisicaDTO.setNome("Alonso");
		pessoaFisicaDTO.setEndereco("FLoripa");
		String json = toJson(pessoaFisicaDTO);
		MvcResult mvcResult = this.mockMvc.perform(post("/v1/api/pessoa/fisica").contentType(contentType).content(json))
				.andExpect(status().isBadRequest()).andReturn();
		Assertions.assertThat(mvcResult.getResponse().getContentAsString())
				.contains(messageBundle.getString("msg.warn.cpf.invalido"));
	}

	@Test
	@Rollback
	public void devePoderDeletarPessoa() throws Exception {
		inserirPessoaFisica();
		this.mockMvc.perform(delete("/v1/api/pessoa/fisica/{cpf}", "05810192530")).andExpect(status().isOk());
	}

	@Test
	@Rollback
	public void devePoderAtualizarDadosCadastrais() throws Exception {
		inserirPessoaFisica();
		PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO();
		pessoaFisicaDTO.setCpf("05810192530");
		pessoaFisicaDTO.setNome("José ATUALIZADO");
		pessoaFisicaDTO.setEndereco("São Paulo");
		this.mockMvc.perform(put("/v1/api/pessoa/fisica").contentType(contentType).content(toJson(pessoaFisicaDTO)))
				.andExpect(status().isOk());
		PessoaFisicaDTO pessoaFisicaAtualizada = findByCPF();
		Assertions.assertThat(pessoaFisicaAtualizada.getNome()).isEqualTo(pessoaFisicaDTO.getNome());
		Assertions.assertThat(pessoaFisicaAtualizada.getEndereco()).isEqualTo(pessoaFisicaDTO.getEndereco());
	}

	@Test
	@Rollback
	public void naoDevePoderAtualizarCPF() throws Exception {
		inserirPessoaFisica();
		PessoaFisicaDTO pessoaFisicaDTO = findByCPF();
		pessoaFisicaDTO.setCpf("38878544035");
		pessoaFisicaDTO.setNome("José Novo");
		pessoaFisicaDTO.setEndereco("Rio de Janeiro");
		MvcResult mvcResult = this.mockMvc
				.perform(put("/v1/api/pessoa/fisica").contentType(contentType).content(toJson(pessoaFisicaDTO)))
				.andExpect(status().isBadRequest()).andReturn();
		Assertions.assertThat(mvcResult.getResponse().getContentAsString())
				.contains(messageBundle.getString("msg.warn.pessoa.nao.encontrada"));
	}

	private PessoaFisicaDTO findByCPF() throws Exception, UnsupportedEncodingException {
		MvcResult mvcResult = this.mockMvc.perform(get("/v1/api/pessoa/fisica/{cpf}", "05810192530"))
				.andExpect(status().isOk()).andReturn();
		PessoaFisicaDTO pessoaFisicaAtualizada = new Gson().fromJson(mvcResult.getResponse().getContentAsString(),
				PessoaFisicaDTO.class);
		return pessoaFisicaAtualizada;
	}

	private void inserirPessoaFisica() throws IOException, Exception {
		PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO();
		pessoaFisicaDTO.setCpf("05810192530");
		pessoaFisicaDTO.setNome("José");
		pessoaFisicaDTO.setEndereco("Floripa");
		String json = toJson(pessoaFisicaDTO);
		this.mockMvc.perform(post("/v1/api/pessoa/fisica").contentType(contentType).content(json))
				.andExpect(status().isCreated());
	}
}
