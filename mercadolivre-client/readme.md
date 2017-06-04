**Application Client**

Essa Aplicação simula um cliente (frontend, serviços, etc...).
Foram criados apenas dois serviços (GET de Pessoa Física e Jurídica) para exemplificar sua funcionalidade.
Mas, nada impede de implementar os demais serviços.

**Arquitetura**
* Spring Boot.
* Hystrix para fallback.
* Redis para cache.

**PASSOS**

* 1 - Baixe o Redis para Windows: https://github.com/rgl/redis/downloads versão redis-2.4.6-setup-64-bit.exe — Redis 2.4.6 Windows Setup (64-bit)

* 2 - Instale o Redis (execute o .exe)

* 3 - Na pasta (na raiz) em que você instalou o redis, abra um prompt de comando.

* 4 - Com o prompt aberto, execute o seguinte comando: redis-server.exe No meu caso, obtive o seguinte output:

C:\Java\redis>redis-server.exe 

[13944] 04 Jun 15:10:06 # Warning: no config file specified, using the default config. In order to` `specify a config file use 'redis-server /path/to/redis.conf'

[13944] 04 Jun 15:10:06 * Server started, Redis version 2.4.6 

[13944] 04 Jun 15:10:06 # Open data file dump.rdb: No such file or directory 

[13944] 04 Jun 15:10:06 * The server is now ready to accept connections on port **6379**


**Pronto, o Redis está de pé! :D**

* 5 - Suba o Microserviço `mercadolivre`.

* 6 - Execute a classe `ClientApplication.java` por `run as > java application`.

* 7 - Essa app está disponível em localhost:8090/pessoa-fisica/{cpf} e localhost:8090/pessoa-fisica/{cnpj}


**REFERÊNCIAS**

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

