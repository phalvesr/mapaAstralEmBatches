package com.ada.mapaAstral.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
@AllArgsConstructor
public class Pessoa {
    private String nome;
    private LocalDate dataNascimento;
    private ZoneId zoneId;
}
