package com.ada.mapaAstral;

import com.ada.mapaAstral.model.Pessoa;
import com.ada.mapaAstral.repository.PessoaRepository;
import com.ada.mapaAstral.service.MapaAstralService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.time.*;
import java.util.List;

@SpringBootApplication
public class MapaAstralApplication implements CommandLineRunner {

	final MapaAstralService mapaAstralService = new MapaAstralService();

	public static void main(String[] args) {
		SpringApplication.run(MapaAstralApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<Pessoa> pessoas = mapaAstralService.getPessoas();

		for(Pessoa pessoa : pessoas) {
			mapaAstralService.gravaInformacoesPessoa(pessoa);
		}
	}
}



