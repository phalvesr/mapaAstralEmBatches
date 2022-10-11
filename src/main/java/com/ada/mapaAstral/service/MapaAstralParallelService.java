package com.ada.mapaAstral.service;

import com.ada.mapaAstral.model.Pessoa;
import com.ada.mapaAstral.repository.MapaAstralRepository;
import com.ada.mapaAstral.type.ArquivoSalvo;
import com.ada.mapaAstral.type.either.Either;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class MapaAstralParallelService {

    private final PessoaService pessoaService;
    private final MapaAstralRepository mapaAstralRepository;
    private final MapaAstralService mapaAstralService;

    /**
     * Gera arquivos para cada linha do CSV utilizando a api de streams
     * */
    public void importarEmParalelo() {
        List<Pessoa> pessoas = pessoaService.buscarPessoas();

        pessoas.stream().parallel()
            .peek(p -> {
                System.out.format("Salvando pessoa na thread %s%n", Thread.currentThread().getName());
            })
            .map(mapaAstralService::montarMapaAstral)
            .forEach(mapaAstralRepository::salvar);

    }

    /**
     * Gera arquivos para cada linha do CSV utilizando o recurso de
     * CompletableFuture e um numero fixo de threads
     * */
    public void importarEmParalelo(int numeroThreads) {

        ExecutorService executor = Executors.newFixedThreadPool(numeroThreads);

        List<Pessoa> pessoas = pessoaService.buscarPessoas();

        var promessas = pessoas.stream()
                .map(mapaAstralService::montarMapaAstral)
                .map(m ->
                    CompletableFuture.supplyAsync(() -> {
                        System.out.println("Executando supply em..." + Thread.currentThread().getName());
                        return mapaAstralRepository.salvar(m);
                    }, executor)
                ).toList();

        promessas.forEach(lidarComTerminoExecucao());
    }

    private static Consumer<CompletableFuture<Either<Exception, ArquivoSalvo>>> lidarComTerminoExecucao() {
        return promessa -> promessa.whenCompleteAsync((resultadoCriacaoMapaAstral, throwable) -> {
            if (throwable != null) {
                System.out.format("Erro inesperado ao executar cricação de mapa astral. Mensagem de erro: %s%n", throwable.getMessage());
                return;
            }

            if (resultadoCriacaoMapaAstral.isLeft()) {
                String mensagemErroSistema = resultadoCriacaoMapaAstral.unsafeGetLeft().getMessage();
                System.out.format("Erro ao executar cricação de mapa astral. Mensagem de erro: %s%n", mensagemErroSistema);
                return;
            }

            System.out.println(resultadoCriacaoMapaAstral.unsafeGetRight().getMessage());
        });
    }
}
