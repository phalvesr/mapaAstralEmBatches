package com.ada.mapaAstral.repository;

import com.ada.mapaAstral.model.MapaAstral;
import com.ada.mapaAstral.model.Pessoa;
import com.ada.mapaAstral.type.ArquivoSalvo;
import com.ada.mapaAstral.type.either.Either;
import com.ada.mapaAstral.type.either.Left;
import com.ada.mapaAstral.type.either.Right;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Repository
public class MapaAstralRepository {

    private final static String HOME_DIR = System.getProperty("user.dir");

    public Either<Exception, ArquivoSalvo> salvar(MapaAstral mapaAstral) {

        try {
            return salvarPessoaEMapaAstralEmArquivo(mapaAstral);
        } catch (IOException e) {
            return Left.create(e);
        }
    }

    private Right<Exception, ArquivoSalvo> salvarPessoaEMapaAstralEmArquivo(MapaAstral mapaAstral) throws IOException {

        String nomeArquivoCSV = getNomeArquivoCSVComNomePessoa(mapaAstral.getPessoa());
        Path path = Paths.get(HOME_DIR, "output", nomeArquivoCSV);

        String conteudoArquivo = gerarConteudoArquivo(mapaAstral);

        Files.deleteIfExists(path);
        Files.createFile(path);

        Files.writeString(
            path,
            conteudoArquivo,
            StandardCharsets.UTF_8,
            StandardOpenOption.TRUNCATE_EXISTING
        );

        return Right.create(
            new ArquivoSalvo(String.format("Arquivo criado no caminho %s", path))
        );
    }

    private String gerarConteudoArquivo(MapaAstral mapaAstral) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("nome,data nascimento,zone id,signo,ascendente,signo lunar")
                .append(System.lineSeparator())
                .append(mapaAstral.toCSV());

        return stringBuilder.toString();
    }

    private String getNomeArquivoCSVComNomePessoa(Pessoa pessoa) {
        return String.format("%s.csv", pessoa.getNome());
    }
}
