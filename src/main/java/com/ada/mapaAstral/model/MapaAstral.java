package com.ada.mapaAstral.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MapaAstral {
    private String signo;
    private String ascendente;
    private String signoLunar;

    @Override
    public String toString() {
        return signo.concat(",").concat(ascendente).concat(",").concat(signoLunar);
    }
}
