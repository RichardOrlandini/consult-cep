package com.richard.cep.controller;


import com.richard.cep.service.CepService;
import com.richard.cep.exception.CepNotFoundException;
import com.richard.cep.model.dto.CepRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cep")
@Validated
public class CepController {

    @Autowired
    private CepService cepService;

    @GetMapping("/{cep}")
    public ResponseEntity<String> consultarCep(@PathVariable("cep") String cep) {
        try {
            return ResponseEntity.ok(cepService.consultarCep(cep));
        } catch (CepNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/consultar")
    public ResponseEntity<String> consultarCepPost(@RequestBody @Valid CepRequest cepRequest) {
        try {
            return ResponseEntity.ok(cepService.consultarCep(cepRequest.getCep()));
        } catch (CepNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
