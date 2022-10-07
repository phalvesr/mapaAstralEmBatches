package com.ada.mapaAstral.repository;

import com.ada.mapaAstral.model.MapaAstral;
import com.ada.mapaAstral.type.ArquivoSalvo;
import com.ada.mapaAstral.type.either.Either;
import com.ada.mapaAstral.type.either.Left;
import com.ada.mapaAstral.type.either.Right;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
            return salvarMapaAstralEmArquivo(mapaAstral);
        } catch (IOException e) {
            return Left.create(e);
        }
    }

    public Either<Exception, ArquivoSalvo> salvar(MapaAstral mapaAstral, String nome) {
        try {
            return salvarMapaAstralEmArquivo(mapaAstral, nome);
        } catch (IOException e) {
            return Left.create(new RuntimeException("Servidor nao pôde processar o arquivo enviado. Aguarde e tente novamente.", e));
        }
    }

    public Either<Exception, InputStream> buscarPorNome(String nome) {
        try {
            return getStreamArquivo(nome);
        } catch (FileNotFoundException e) {
            return Left.create(new RuntimeException(String.format("Mapa astral nome %s não encontrado", nome)));
        } catch (IOException e) {
            return Left.create(e);
        }
    }

    private Either<Exception, InputStream> getStreamArquivo(String nome) throws IOException {

        String nomeArquivoCSV = getNomeArquivoCSV(nome);
        Path caminho = Paths.get(HOME_DIR, "output", "upload", nomeArquivoCSV);

        InputStream inputStream = Files.newInputStream(caminho, StandardOpenOption.READ);

        return Right.create(inputStream);
    }

    private Either<Exception, ArquivoSalvo> salvarMapaAstralEmArquivo(MapaAstral mapaAstral, String nome) throws IOException {
        String nomeArquivoCSV = getNomeArquivoCSV(nome);
        Path caminho = Paths.get(HOME_DIR, "output", "upload", nomeArquivoCSV);

        String conteudo = gerarConteudoArquivo(mapaAstral);

        deletarArquivoEmCasoDeExistencia(caminho);
        salvarConteudoEmArquivo(conteudo, caminho);

        return Right.create(new ArquivoSalvo(String.format("Arquivo nome %s salvo com sucesso!", nome)));
    }

    private Right<Exception, ArquivoSalvo> salvarMapaAstralEmArquivo(MapaAstral mapaAstral) throws IOException {

        String nomeArquivoCSV = getNomeArquivoCSV(mapaAstral.getPessoa().getNome());
        Path caminho = Paths.get(HOME_DIR, "output", nomeArquivoCSV);

        String conteudoArquivo = gerarConteudoArquivo(mapaAstral);

        deletarArquivoEmCasoDeExistencia(caminho);
        salvarConteudoEmArquivo(conteudoArquivo, caminho);

        return Right.create(
            new ArquivoSalvo(String.format("Arquivo criado no caminho %s", caminho))
        );
    }

    private static void deletarArquivoEmCasoDeExistencia(Path caminho) throws IOException {
        Files.deleteIfExists(caminho);
    }

    private static void salvarConteudoEmArquivo(String conteudoArquivo, Path caminho) throws IOException {

        Files.createFile(caminho);

        Files.writeString(
                caminho,
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
