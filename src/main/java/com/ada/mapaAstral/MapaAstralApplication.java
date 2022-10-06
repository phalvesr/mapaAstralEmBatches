package com.ada.mapaAstral;

import com.ada.mapaAstral.model.Pessoa;
import com.ada.mapaAstral.repository.PessoaRepository;
import com.ada.mapaAstral.service.MapaAstralService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MapaAstralApplication implements CommandLineRunner {

	private final PessoaRepository pessoaRepository = new PessoaRepository();
	private final MapaAstralService mapaAstralService = new MapaAstralService(pessoaRepository);

	public static void main(String[] args) {
		SpringApplication.run(MapaAstralApplication.class, args);
	}

	@Override
	public void run(String... args) {
		List<Pessoa> pessoas = mapaAstralService.getPessoas();

		pessoas.forEach(mapaAstralService::gravaInformacoesPessoa);
	}
}



