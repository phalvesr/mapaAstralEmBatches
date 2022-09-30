package com.ada.mapaAstral;

import com.ada.mapaAstral.service.MapaAstralService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.*;

@SpringBootApplication
public class MapaAstralApplication {

	public static void main(String[] args) {

		LocalDateTime localDateTimeLucas = LocalDateTime.of(1992, Month.DECEMBER, 16, 12, 35);
		LocalDateTime localDateTimeTomas = LocalDateTime.of(1999, Month.AUGUST, 30, 12, 30);
		LocalDateTime localDateTimeArthur = LocalDateTime.of(1987, Month.MARCH, 25, 19, 30);
		LocalDateTime localDateTimeIngrid = LocalDateTime.of(1990, Month.SEPTEMBER, 17, 18, 30);

		MapaAstralService mapaAstralService = new MapaAstralService();
		//LocalDate localDateLucas = LocalDate.of(localDateTimeLucas.getYear(),localDateTimeLucas.getMonth(),localDateTimeLucas.getDayOfMonth());

		//Lucas

		String signoLucas = mapaAstralService.buscaPorSigno(localDateTimeLucas.toLocalDate());
		String signoAscendenteLucas = mapaAstralService.procurarAscendente(signoLucas, LocalTime.from(localDateTimeLucas));
		String buscaSignoPorEnunLucas = mapaAstralService.buscaSignoPorEnun(localDateTimeLucas);
		mapaAstralService.mapaAstral(localDateTimeLucas);

		//Ingrid

		String signoIngrid = mapaAstralService.buscaPorSigno(localDateTimeIngrid.toLocalDate());
		String signoAscendenteIngrid = mapaAstralService.procurarAscendente(signoIngrid, LocalTime.from(localDateTimeIngrid));
		String buscaSignoPorEnunIngrid = mapaAstralService.buscaSignoPorEnun(localDateTimeIngrid);
		mapaAstralService.mapaAstral(localDateTimeIngrid);
	}
}



