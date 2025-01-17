package com.richard.cep;

import com.richard.cep.service.CepService;
import com.richard.cep.model.CepLog;
import com.richard.cep.repository.CepLogRepository;
import com.richard.cep.exception.CepNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CepApplicationTests {

    @Mock
    private CepLogRepository cepLogRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CepService cepService;

    @Test
    public void testConsultaCepValido() {
        // Simulando o comportamento do RestTemplate com dados completos
        String cep = "01001000"; // CEP válido
        String dadosEsperados = "{"
                + "\"cep\":\"01001-000\","
                + "\"logradouro\":\"Praça da Sé\","
                + "\"complemento\":\"lado ímpar\","
                + "\"unidade\":\"\","
                + "\"bairro\":\"Sé\","
                + "\"localidade\":\"São Paulo\","
                + "\"uf\":\"SP\","
                + "\"estado\":\"São Paulo\","
                + "\"regiao\":\"Sudeste\","
                + "\"ibge\":\"3550308\","
                + "\"gia\":\"1004\","
                + "\"ddd\":\"11\","
                + "\"siafi\":\"7107\""
                + "}";

        // Simula a resposta do RestTemplate
        when(restTemplate.getForObject("https://viacep.com.br/ws/" + cep + "/json/", String.class))
                .thenReturn(dadosEsperados);

        // Simulando a persistência do log
        CepLog logEsperado = new CepLog(cep, dadosEsperados);
        when(cepLogRepository.save(any(CepLog.class))).thenReturn(logEsperado);

        // Chama o método de consulta ao CEP
        String dadosRetornados = cepService.consultarCep(cep);

        // Verificando se a resposta não é nula e contém os dados esperados
        assertNotNull(dadosRetornados);
        assertTrue(dadosRetornados.contains("Praça da Sé"));
        assertTrue(dadosRetornados.contains("lado ímpar"));
        assertTrue(dadosRetornados.contains("São Paulo"));
        assertTrue(dadosRetornados.contains("SP"));
        assertTrue(dadosRetornados.contains("Sudeste"));
        assertTrue(dadosRetornados.contains("3550308"));

        // Verificando se o repositório foi chamado para salvar o log
        verify(cepLogRepository, times(1)).save(any(CepLog.class));
    }

    @Test
    public void testConsultaCepInvalido() {
        String cepInvalido = "00000000"; // CEP inválido

        // Simulando o comportamento do RestTemplate para retornar null (CEP não encontrado)
        when(restTemplate.getForObject("https://viacep.com.br/ws/" + cepInvalido + "/json/", String.class))
                .thenReturn(null);

        // Verificando se a exceção CepNotFoundException é lançada
        CepNotFoundException exception = assertThrows(CepNotFoundException.class, () -> {
            cepService.consultarCep(cepInvalido);
        });

        // Verificando a mensagem da exceção
        assertEquals("CEP não encontrado: " + cepInvalido, exception.getMessage());
    }

    @Test
    public void testValidaFormatoCep() {
        String cepComFormatoErrado = "12345";

        // Verificando se a exceção é lançada quando o formato do CEP estiver errado
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cepService.consultarCep(cepComFormatoErrado);
        });

        assertEquals("CEP inválido, formato esperado: XXXXX-XXX", exception.getMessage());
    }

    @Test
    public void testPersistenciaLogConsulta() {
        String cep = "01001000"; // CEP válido
        String dadosRetornados = "{"
                + "\"cep\":\"01001-000\","
                + "\"logradouro\":\"Praça da Sé\","
                + "\"complemento\":\"lado ímpar\","
                + "\"unidade\":\"\","
                + "\"bairro\":\"Sé\","
                + "\"localidade\":\"São Paulo\","
                + "\"uf\":\"SP\","
                + "\"estado\":\"São Paulo\","
                + "\"regiao\":\"Sudeste\","
                + "\"ibge\":\"3550308\","
                + "\"gia\":\"1004\","
                + "\"ddd\":\"11\","
                + "\"siafi\":\"7107\""
                + "}";

        // Simulando a resposta do RestTemplate
        when(restTemplate.getForObject("https://viacep.com.br/ws/" + cep + "/json/", String.class))
                .thenReturn(dadosRetornados);

        // Simulando a persistência do log
        CepLog logEsperado = new CepLog(cep, dadosRetornados);
        when(cepLogRepository.save(any(CepLog.class))).thenReturn(logEsperado);

        // Chama o método de consulta ao CEP
        cepService.consultarCep(cep);

        // Verificando se o repositório foi chamado para salvar o log
        verify(cepLogRepository, times(1)).save(any(CepLog.class));
    }

    @Test
    public void testCepComRespostaVazia() {
        String cep = "01001000"; // CEP válido, mas resposta da API será vazia

        // Simulando o comportamento do RestTemplate para retornar uma resposta vazia
        when(restTemplate.getForObject("https://viacep.com.br/ws/" + cep + "/json/", String.class))
                .thenReturn(""); // Resposta vazia

        // Verificando se a exceção CepNotFoundException é lançada quando a resposta é vazia
        CepNotFoundException exception = assertThrows(CepNotFoundException.class, () -> {
            cepService.consultarCep(cep);
        });

        // Verificando a mensagem da exceção
        assertEquals("CEP não encontrado: " + cep, exception.getMessage());
    }

    @Test
    public void testRestTemplateErro() {
        String cep = "01001000"; // CEP válido

        // Simulando o erro do RestTemplate (exemplo: timeout ou falha na consulta)
        when(restTemplate.getForObject("https://viacep.com.br/ws/" + cep + "/json/", String.class))
                .thenThrow(new RuntimeException("Erro na consulta da API"));

        // Verificando se a exceção RuntimeException é lançada
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cepService.consultarCep(cep);
        });

        // Verificando a mensagem da exceção
        assertEquals("Erro na consulta da API", exception.getMessage());
    }
}
