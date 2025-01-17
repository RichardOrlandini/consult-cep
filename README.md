
# Consulta de CEP

Este projeto é uma aplicação simples que consulta um CEP usando a API ViaCEP, armazena os resultados em um banco de dados, e valida o formato do CEP. A aplicação segue os princípios de desenvolvimento SOLID e implementa uma API REST para realizar a consulta.

## Funcionalidades

- **Consulta de CEP**: Realiza uma consulta em uma API externa (ViaCEP).
- **Validação de Formato de CEP**: Valida se o CEP está no formato correto `XXXXX-XXX`.
- **Persistência de Logs**: Armazena o log de cada consulta no banco de dados, incluindo o CEP consultado, os dados retornados da API e a data da consulta.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para desenvolvimento da aplicação.
- **JPA/Hibernate**: Para persistência de dados.
- **H2 Database**: Banco de dados em memória para desenvolvimento e testes.
- **Mockito**: Framework de testes para simulação de dependências.
- **REST API**: Para fornecer a funcionalidade de consulta de CEP via HTTP.
- **JSON**: Formato de dados usado para comunicar com a API ViaCEP e armazenar os logs.

## Endpoints

A aplicação fornece os seguintes endpoints:

### 1. **Consulta de CEP (GET)**
**Endpoint**: `/api/cep/{cep}`  
**Descrição**: Realiza a consulta ao CEP informado.  
**Resposta**:
- **Código 200**: Retorna os dados do endereço.
- **Código 404**: Caso o CEP não seja encontrado.

**Exemplo de URL**:
   ```
   GET http://localhost:8080/api/cep/01001-000
   ```

**Exemplo de Resposta (200 OK)**:
   ```json
   {
     "cep": "01001-000",
     "logradouro": "Praça da Sé",
     "complemento": "lado ímpar",
     "unidade": "",
     "bairro": "Sé",
     "localidade": "São Paulo",
     "uf": "SP",
     "estado": "São Paulo",
     "regiao": "Sudeste",
     "ibge": "3550308",
     "gia": "1004",
     "ddd": "11",
     "siafi": "7107"
   }
   ```

### 2. **Consulta de CEP (POST)**
**Endpoint**: `/api/cep/consultar`  
**Descrição**: Envia o CEP no corpo da requisição e realiza a consulta.

**Exemplo de Corpo da Requisição**:
   ```json
   {
     "cep": "01001-000"
   }
   ```

**Exemplo de Resposta (200 OK)**:
   ```json
   {
     "cep": "01001-000",
     "logradouro": "Praça da Sé",
     "complemento": "lado ímpar",
     "unidade": "",
     "bairro": "Sé",
     "localidade": "São Paulo",
     "uf": "SP",
     "estado": "São Paulo",
     "regiao": "Sudeste",
     "ibge": "3550308",
     "gia": "1004",
     "ddd": "11",
     "siafi": "7107"
   }
   ```

## Instruções de Execução

### 1. **Clonar o Repositório**
Primeiro, clone o repositório para o seu ambiente local:
   ```bash
   git clone https://github.com/seu-usuario/consulta-cep.git
   ```

### 2. **Configuração do Projeto**
- **Banco de Dados**: O projeto usa o banco de dados **H2** em memória, então não há necessidade de configuração externa.
- **Configuração do Spring Boot**: As configurações padrão estão definidas em **`application.properties`**. A configuração do banco de dados será gerada automaticamente.

### 3. **Executar a Aplicação**
Se você estiver usando **Maven**, execute o seguinte comando para rodar a aplicação:
   ```bash
   mvn spring-boot:run
   ```

Ou, se estiver utilizando **IntelliJ** ou **Eclipse**, basta clicar em **Run** para iniciar o servidor.

### 4. **Testando os Endpoints**
Após iniciar o servidor, você pode testar os endpoints utilizando o **Postman**, **Insomnia** ou **cURL**.

**Exemplo de cURL para consultar o CEP**:
   ```bash
   curl -X GET http://localhost:8080/api/cep/01001-000
   ```

**Exemplo de cURL para consulta via POST**:
   ```bash
   curl -X POST http://localhost:8080/api/cep/consultar -H "Content-Type: application/json" -d '{"cep": "01001-000"}'
   ```

## Testes

A aplicação contém testes unitários para garantir que todos os componentes funcionem como esperado. Para rodar os testes, use o seguinte comando Maven:
```bash
mvn test
```
Os testes verificam a consulta ao CEP, o formato do CEP, o tratamento de erros e a persistência dos logs no banco de dados.
