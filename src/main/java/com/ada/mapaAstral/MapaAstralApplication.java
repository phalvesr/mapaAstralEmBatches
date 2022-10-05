package com.ada.mapaAstral;

import com.ada.mapaAstral.model.Pessoa;
import com.ada.mapaAstral.repository.PessoaRepository;
import com.ada.mapaAstral.service.MapaAstralService;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.*;
import java.util.List;

@SpringBootApplication
public class MapaAstralApplication {

	@SneakyThrows
	public static void main(String[] args) {

		MapaAstralService mapaAstralService = new MapaAstralService();
		//LocalDate localDateLucas = LocalDate.of(localDateTimeLucas.getYear(),localDateTimeLucas.getMonth(),localDateTimeLucas.getDayOfMonth());

		PessoaRepository pessoaRepository = new PessoaRepository();
		List<Pessoa> pessoas = pessoaRepository.getPessoas();


	}
}



