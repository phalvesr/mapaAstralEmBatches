package com.ada.mapaAstral;

import com.ada.mapaAstral.repository.MapaAstralRepository;
import com.ada.mapaAstral.repository.PessoaRepository;
import com.ada.mapaAstral.service.MapaAstralParallelService;
import com.ada.mapaAstral.service.MapaAstralService;
import com.ada.mapaAstral.service.PessoaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MapaAstralApplication implements CommandLineRunner {

	private final MapaAstralRepository mapaAstralRepository = new MapaAstralRepository();
	private final PessoaRepository pessoaRepository = new PessoaRepository();
	private final PessoaService pessoaService = new PessoaService(pessoaRepository);
	private final MapaAstralService mapaAstralService = new MapaAstralService(mapaAstralRepository, pessoaService);
	private final MapaAstralParallelService mapaAstralParallel = new MapaAstralParallelService(pessoaService, mapaAstralRepository, mapaAstralService);

	public static void main(String[] args) {
		SpringApplication.run(MapaAstralApplication.class, args);
	}

	@Override
	public void run(String... args) {
		mapaAstralParallel.importarEmParalelo();
		mapaAstralParallel.importarEmParalelo(2);
	}
}



