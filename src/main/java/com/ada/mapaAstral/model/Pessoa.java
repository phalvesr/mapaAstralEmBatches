package com.ada.mapaAstral.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
public class Pessoa implements CSVConvertible {
    private String nome;
    private LocalDateTime dataNascimento;
    private ZoneId zoneId;

    @Override
    public String toCSV() {
        return String.format("%s,%s,%s", nome, dataNascimento, zoneId);
    }
}
