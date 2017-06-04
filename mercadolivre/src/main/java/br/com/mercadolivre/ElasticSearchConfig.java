package br.com.mercadolivre;
import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableElasticsearchRepositories(basePackages = "br.com.mercadolivre.domain.elasticsearch.repositories")
@EnableJpaRepositories(basePackages = "br.com.mercadolivre.domain.jpa.repositories")
public class ElasticSearchConfig {
	@Value("${elasticsearch.host}")
	private String EsHost;

	@Value("${elasticsearch.port}")
	private int EsPort;

	@Value("${elasticsearch.clustername}")
	private String EsClusterName;

	@Bean
	public Client client() throws Exception {

		Settings esSettings = Settings.settingsBuilder().put("cluster.name", EsClusterName).build();
		System.out.println("##################################################");
		System.out.println("conectando-se com o elasticsearch... isso pode demorar um pouco. :-)");
		System.out.println("##################################################");
		return TransportClient.builder().settings(esSettings).build()
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));
	}

	@Bean
	public ElasticsearchOperations elasticsearchTemplate() throws Exception {
		return new ElasticsearchTemplate(client());
	}

}

