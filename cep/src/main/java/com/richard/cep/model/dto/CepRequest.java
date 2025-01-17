package com.richard.cep.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CepRequest {

    @NotEmpty(message = "CEP não pode ser vazio")
    @Pattern(regexp = "\\d{5}-?\\d{3}", message = "CEP inválido, formato esperado: XXXXX-XXX")
    private String cep;
}
