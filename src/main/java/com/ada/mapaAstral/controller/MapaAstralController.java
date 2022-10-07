package com.ada.mapaAstral.controller;

import com.ada.mapaAstral.service.MapaAstralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/mapa-astral")
@RequiredArgsConstructor
public class MapaAstralController {

    private final MapaAstralService service;

    @GetMapping("/files/{nome}")
    public ResponseEntity<?> buscaMapaAstral(@PathVariable String nome) throws IOException {
        var resultadoBusca = service.buscarPorNome(nome);

        if (resultadoBusca.isLeft()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/plain; charset=utf-8")
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\\%s", nome))
                .body(resultadoBusca.unsafeGetRight());
    }

    @PostMapping()
    public ResponseEntity<?> criarMapaAstral(@RequestParam("CsvFile") MultipartFile file, UriComponentsBuilder uriBuilder) {

        var resultadosArquivoSalvo = service.lidaComArquivoEnviado(file);

        return resultadosArquivoSalvo.match(
            onFormatoArquivoNaoSuportado -> ResponseEntity.badRequest().body(onFormatoArquivoNaoSuportado.getMessage()),
            onCSVEnviadoComFormatoInvalido -> ResponseEntity.badRequest().body(onCSVEnviadoComFormatoInvalido.getMessage()),
            onProblemaIOServidor -> ResponseEntity.internalServerError().body(onProblemaIOServidor.getMessage()),
            onArquivoSalvo -> {
                URI uri = uriBuilder.path("/{id}").buildAndExpand(onArquivoSalvo.code()).toUri();
                return ResponseEntity.created(uri).body(onArquivoSalvo);
            }
        );
    }
}
