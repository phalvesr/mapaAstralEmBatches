package com.ada.mapaAstral.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter
public class Pessoa {
    private String nome;
    private LocalDate dataNascimento;
    private ZoneId zoneId;
}
