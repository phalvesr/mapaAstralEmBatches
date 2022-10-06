package com.ada.mapaAstral.repository;

import com.ada.mapaAstral.model.MapaAstral;
import com.ada.mapaAstral.model.Pessoa;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PessoaRepository {

    private final static String HOME_DIR = System.getProperty("user.dir");

    private final static String caminhoArquivoEntrada = Paths.get(HOME_DIR, "src", "pessoas.csv").toString();

    public List<Pessoa> getPessoas() throws IOException {

        Path path = Paths.get(caminhoArquivoEntrada);

        return Files.lines(path, StandardCharsets.UTF_8)
                .map(this::createPessoaFromLine)
                .collect(Collectors.toList());
    }

    private Pessoa createPessoaFromLine(String line) {
        try (Scanner scanner = new Scanner(line)) {
            scanner.useDelimiter(",");

            String nome = scanner.next();
            ZoneId zoneId = ZoneId.of(scanner.next());
            LocalDateTime dataNascimento = LocalDateTime.parse(scanner.next());

            return new Pessoa(nome, dataNascimento, zoneId);
        }
    }

    private void createArquivoGravaPessoa(Pessoa pessoa, MapaAstral mapaAstral) {
        String caminhoArquivoSaida = Paths.get(HOME_DIR, "src", pessoa.getNome()).toString();

        Path pathArquivoSaida = Paths.get(caminhoArquivoSaida + ".csv");
        Files.createFile(pathArquivoSaida);
    }
}
