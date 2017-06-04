**Arquitetura**
* Spring Boot.
* Spring Data JPA.
* Spring Data ElasticSearch.
* Swagger para documentação.
**Obs: Arquitetura representada pelo arquivo arquitetura-UML.png**
#Passos:
 
**1 - Ter uma instância do MySQL rodando em localhost:3306**

* 1.1 - Executar o script `startup.sql` no MySQL. 
* 1.2. - Usuário tem que ser **"root"** e **sem senha**.
* 1.3 - Ou altere usuário e senha no `application.properties` (spring.datasource.username e spring.datasource.password)

**2 - Ter uma instância do ElasticSearch 2.4.0 rodando em localhost:9300**
* 2.1. - Ter clustername no ElasticSearch de "mercadolivre" (C:\elasticsearch-2.4.0\config\elasticsearch.yml)
`cluster.name: mercadolivre`

**3 - Documentação de todos endpoints**

Todos endpoints aqui feitos estão documentados pela API Swagger.
Basta subir a aplicação e acessar: http://localhost:8080/ml/swagger-ui.html :-)

**Testing**

* Para testes, é necessário subir uma outra instância do ElasticSearch
* Essa instância precisa rodar com as seguintes configurações (`C:\elasticsearch-2.4.0\config\elasticsearch.yml`):
  * `cluster.name: mercadolivre`
  * `transport.publish_port: 9600`
  * `transport.tcp.port: 9600`
  * `http.port: 9700`

* Para executar os testes, basta acessar a classe PessoaFisicaInteTest/PessoaJuridicaInteTest e executá-la como JUnit.


**REFERÊNCIAS**

* **ELASTICSEARCH**
  * https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#elasticsearch.repositories
  * https://projects.spring.io/spring-data-elasticsearch/
  * http://www.baeldung.com/spring-data-elasticsearch-tutorial
  * https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-transport.html
  * https://www.mkyong.com/spring-boot/spring-boot-spring-data-elasticsearch-example/

* **LOAD BALANCE RIBBON** 
  * https://spring.io/guides/gs/client-side-load-balancing/

* **CIRCUIT BREAKER (HYSTRIX)** 
  * https://spring.io/guides/gs/circuit-breaker/
  * https://github.com/spring-guides/gs-circuit-breaker/tree/master/complete

* **SWAGGER**
  * http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api

* **REDIS**
  * https://github.com/rgl/redis/downloads
  * http://www.baeldung.com/spring-data-redis-tutorial
  * https://examples.javacodegeeks.com/enterprise-java/spring/spring-data-redis-example/
