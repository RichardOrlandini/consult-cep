package com.richard.cep.service;

import com.richard.cep.model.CepLog;
import com.richard.cep.repository.CepLogRepository;
import com.richard.cep.exception.CepNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@Service
public class CepService {

    @Autowired
    private CepLogRepository cepLogRepository;

    @Autowired
    private  RestTemplate restTemplate;

    public String consultarCep(@Valid String cep) {

        if (!cep.matches("\\d{5}-?\\d{3}")) {
            throw new IllegalArgumentException("CEP inválido, formato esperado: XXXXX-XXX");
        }

        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        String dadosRetornados = restTemplate.getForObject(url, String.class);

        if (dadosRetornados == null || dadosRetornados.isEmpty()) {
            throw new CepNotFoundException("CEP não encontrado: " + cep);
        }

        CepLog cepLog = new CepLog(cep, dadosRetornados);
        cepLogRepository.save(cepLog);

        return dadosRetornados;
    }
}
