package com.ada.mapaAstral.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@AllArgsConstructor
public class Pessoa {
    private String nome;
    private LocalDateTime dataNascimento;
    private ZoneId zoneId;

    @Override
    public String toString() {
        return nome.concat(",").concat(dataNascimento.toString()).concat(",").concat(zoneId.toString());
    }
}
