package com.ada.mapaAstral.repository;

import com.ada.mapaAstral.model.Pessoa;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PessoaRepository {

    private final static String HOME_DIR = System.getProperty("user.dir");

    private final static String caminhoArquivo = Paths.get(HOME_DIR, "src", "pessoas.csv").toString();

    // TODO Trocar para List<Pessoas> (ler linha a linha e criar uma pessoa)
    public List<Pessoa> getPessoas() throws IOException {

        Path path = Paths.get(caminhoArquivo);

        return Files.lines(path, StandardCharsets.UTF_8)
                .map(this::createPessoaFromLine)
                .collect(Collectors.toList());
    }

    private Pessoa createPessoaFromLine(String line) {
        try (Scanner scanner = new Scanner(line)) {
            scanner.useDelimiter(",");

            String nome = scanner.next();
            ZoneId zoneId = ZoneId.of(scanner.next());
            LocalDate dataNascimento = LocalDate.parse(scanner.next());

            return new Pessoa(nome, dataNascimento, zoneId);
        }
    }
}
