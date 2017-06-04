package br.com.mercadolivre;

import java.io.IOException;
import java.util.Arrays;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.client.TestRestTemplate.HttpClientOption;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mercadolivre.util.MessageBundle;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public abstract class SpringTest {
    
	private static final String SERVER_ADDRESS = "http://localhost:";
	private static final String SERVER_CONTEXT = "/ml";
	
	@LocalServerPort 
	private int porta;
	protected String url;
	
	@Autowired
	protected MessageBundle messageBundle;
	
	@Autowired
	private ElasticsearchTemplate esTemplate;
	
	protected TestRestTemplate restTemplate = new TestRestTemplate("user", "password", HttpClientOption.ENABLE_COOKIES);
    protected ObjectMapper jsonMapper = new ObjectMapper();
	@SuppressWarnings("rawtypes")
	protected HttpMessageConverter mappingJackson2HttpMessageConverter;

	protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON_UTF8.getType(), MediaType.APPLICATION_JSON_UTF8.getSubtype());

	public void configureUrl(String urlParam) {
    	this.url = getServerPath().concat(urlParam);
    }
    
    private String getServerPath() {
    	return SERVER_ADDRESS + porta + SERVER_CONTEXT;
    }
    
	public HttpEntity<String> configureHttpEntity() {
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		return entity;
	}
    
	@SuppressWarnings("unchecked")
	protected String toJson(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

	public TestRestTemplate getRestTemplate() {
		return restTemplate;
	}

	public String getUrl() {
		return url;
	}

	public ObjectMapper getJsonMapper() {
		return jsonMapper;
	}
	
	protected void createElasticSearchIndex(Class<?> clazz) {
		esTemplate.createIndex(clazz);
		esTemplate.putMapping(clazz);
		esTemplate.refresh(clazz);
	}
	
	protected void destroyElasticSearchIndex(Class<?> clazz){
		esTemplate.deleteIndex(clazz);
	}
}
