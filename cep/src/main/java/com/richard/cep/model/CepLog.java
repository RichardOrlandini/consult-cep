package com.richard.cep.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CepLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "CEP não pode ser nulo")
    @NotEmpty(message = "CEP não pode ser vazio")
    private String cep;

    @NotNull(message = "Dados retornados não podem ser nulos")
    @NotEmpty(message = "Dados retornados não podem ser vazios")
    @Column(length = 1000)
    private String dadosRetornados;

    private LocalDateTime dataConsulta;

    public CepLog(String cep, String dadosRetornados) {
        this.cep = cep;
        this.dadosRetornados = dadosRetornados;
        this.dataConsulta = LocalDateTime.now();
    }
}
