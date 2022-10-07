package com.ada.mapaAstral.controller;

import com.ada.mapaAstral.service.MapaAstralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/mapa-astral")
@RequiredArgsConstructor
public class MapaAstralController {

    private final MapaAstralService service;

    @PostMapping()
    public ResponseEntity<?> criarMapaAstral(@RequestParam("CsvFile") MultipartFile file, UriComponentsBuilder uriBuilder) {

        var resultadosArquivoSalvo = service.lidaComArquivoEnviado(file);

        return resultadosArquivoSalvo.match(
            notSupportedFileFormat -> ResponseEntity.badRequest().body(notSupportedFileFormat.getMessage()),
            badFormatCSVFile -> ResponseEntity.badRequest().body(badFormatCSVFile.getMessage()),
            serverIOProblem -> ResponseEntity.internalServerError().body(serverIOProblem.getMessage()),
            created -> {
                URI uri = uriBuilder.path("/{id}").buildAndExpand(created.code()).toUri();
                return ResponseEntity.created(uri).body(created);
            }
        );
    }
}
