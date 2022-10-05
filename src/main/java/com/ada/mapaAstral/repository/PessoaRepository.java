package com.ada.mapaAstral.repository;

import com.ada.mapaAstral.model.Pessoa;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PessoaRepository {

    private static String HOME_DIR = System.getProperty("user.dir");

    String caminhoArquivo = HOME_DIR.concat("/src/pessoas.txt");

    // TODO Trocar para List<Pessoas> (ler linha a linha e criar 
    public List<String> getListaPessoas() {

        Path path = Paths.get(caminhoArquivo);

        if (!Files.exists(path)) {
            System.out.println("Arquivo não existente");
        }

        final List<String> listaPessoas;
        try {
             listaPessoas = Files.readAllLines(path);
            System.out.println(listaPessoas);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listaPessoas;
    }
}
