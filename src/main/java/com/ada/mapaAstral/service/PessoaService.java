package com.ada.mapaAstral.service;

import com.ada.mapaAstral.model.Pessoa;
import com.ada.mapaAstral.repository.PessoaRepository;
import com.ada.mapaAstral.type.either.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;

    public List<Pessoa> buscarPessoas() {

        Either<Exception, List<Pessoa>> resultadoBuscaPessoas =  repository.findAll();

        if (resultadoBuscaPessoas.isLeft()) {
            System.out.println(resultadoBuscaPessoas.unsafeGetLeft().getMessage());
            return Collections.emptyList();
        } else {
            return resultadoBuscaPessoas.unsafeGetRight();
        }
    }
}
