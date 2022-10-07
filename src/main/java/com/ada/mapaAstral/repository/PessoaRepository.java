package com.ada.mapaAstral.repository;

import com.ada.mapaAstral.model.CSVConvertible;
import com.ada.mapaAstral.model.MapaAstral;
import com.ada.mapaAstral.model.Pessoa;
import com.ada.mapaAstral.type.ArquivoSalvo;
import com.ada.mapaAstral.type.either.Either;
import com.ada.mapaAstral.type.either.Left;
import com.ada.mapaAstral.type.either.Right;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class PessoaRepository {

    private final static String HOME_DIR = System.getProperty("user.dir");

    public Either<Exception, List<Pessoa>> findAll() {
        try {
            return getPessoasArquivo();
        } catch (Exception e) {
            return Left.create(e);
        }
    }

    private Pessoa createPessoaFromLine(String line) {

        try (Scanner scanner = new Scanner(line)) {
            scanner.useDelimiter(",");

            String nome = scanner.next().trim();
            ZoneId zoneId = ZoneId.of(scanner.next());
            LocalDateTime dataNascimento = LocalDateTime.parse(scanner.next());

            return new Pessoa(nome, dataNascimento, zoneId);
        }
    }

    private String getNomeArquivoCSVComNomePessoa(Pessoa pessoa) {
        return String.format("%s.csv", pessoa.getNome());
    }

    private Right<Exception, List<Pessoa>> getPessoasArquivo() throws IOException {
        Path path = Paths.get(HOME_DIR, "input", "pessoas.csv");

        try (Stream<String> fileStream = Files.lines(path, StandardCharsets.UTF_8)) {

            var pessoas = fileStream
                    .map(this::createPessoaFromLine)
                    .toList();

            return Right.create(pessoas);
        }
    }
}


