package com.ada.mapaAstral.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MapaAstral implements CSVConvertible {
    private String signo;
    private String ascendente;
    private String signoLunar;
    private Pessoa pessoa;

    @Override
    public String toCSV() {
        return String.format("%s,%s,%s,%s", pessoa.toCSV(), signo, ascendente, signoLunar);
    }
}
