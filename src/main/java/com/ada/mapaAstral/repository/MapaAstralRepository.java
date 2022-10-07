package com.ada.mapaAstral.repository;

import com.ada.mapaAstral.model.MapaAstral;
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

    public Either<Exception, ArquivoSalvo> salvar(MapaAstral mapaAstral, String nome) {
        try {
            return salvarPessoaEMapaAstralEmArquivo(mapaAstral, nome);
        } catch (IOException e) {
            return Left.create(new RuntimeException("Servidor nao pôde processar o arquivo enviado. Aguarde e tente novamente.", e));
        }
    }

    private Either<Exception, ArquivoSalvo> salvarPessoaEMapaAstralEmArquivo(MapaAstral mapaAstral, String nome) throws IOException {
        String nomeArquivoCSV = getNomeArquivoCSV(nome);
        Path path = Paths.get(HOME_DIR, "output", "upload", nomeArquivoCSV);

        String conteudo = gerarConteudoArquivo(mapaAstral);

        deletarArquivoEmCasoDeExistencia(path);
        salvarConteudoEmArquivo(conteudo, path);

        return Right.create(new ArquivoSalvo(String.format("Arquivo nome %s salvo com sucesso!", nome)));
    }

    private Right<Exception, ArquivoSalvo> salvarPessoaEMapaAstralEmArquivo(MapaAstral mapaAstral) throws IOException {

        String nomeArquivoCSV = getNomeArquivoCSV(mapaAstral.getPessoa().getNome());
        Path path = Paths.get(HOME_DIR, "output", nomeArquivoCSV);

        String conteudoArquivo = gerarConteudoArquivo(mapaAstral);

        deletarArquivoEmCasoDeExistencia(path);
        salvarConteudoEmArquivo(conteudoArquivo, path);

        return Right.create(
            new ArquivoSalvo(String.format("Arquivo criado no caminho %s", path))
        );
    }

    private static void deletarArquivoEmCasoDeExistencia(Path path) throws IOException {
        Files.deleteIfExists(path);
    }

    private static void salvarConteudoEmArquivo(String conteudoArquivo, Path path) throws IOException {

        Files.createFile(path);

        Files.writeString(
                path,
                conteudoArquivo,
            StandardCharsets.UTF_8,
            StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    private String gerarConteudoArquivo(MapaAstral mapaAstral) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("nome,data nascimento,zone id,signo,ascendente,signo lunar")
                .append(System.lineSeparator())
                .append(mapaAstral.toCSV());

        return stringBuilder.toString();
    }

    private String getNomeArquivoCSV(String nome) {
        return String.format("%s.csv", nome);
    }
}
